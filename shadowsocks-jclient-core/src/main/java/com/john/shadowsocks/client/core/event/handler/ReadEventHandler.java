package com.john.shadowsocks.client.core.event.handler;

import com.john.shadowsocks.client.core.JavaClientServer;
import com.john.shadowsocks.client.core.codec.Encryptor;
import com.john.shadowsocks.client.core.event.data.ReadEvent;
import com.john.shadowsocks.client.core.event.data.WriteEvent;
import com.john.shadowsocks.client.core.session.ChannelData;
import com.john.shadowsocks.client.core.session.ChannelType;
import com.john.shadowsocks.client.core.session.SocketSession;
import com.john.shadowsocks.client.core.sockets5.Socks5HandleResult;
import com.john.shadowsocks.client.core.sockets5.Socks5HandlerFactory;
import com.john.shadowsocks.client.core.sockets5.Socks5StageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @author jiangguangtao on 2016/5/24.
 */
public class ReadEventHandler implements EventHandler<ReadEvent> {
    private static final Logger log = LoggerFactory.getLogger(ReadEventHandler.class);
    private static final ReadEventHandler instance = new ReadEventHandler();

    private ReadEventHandler() {

    }

    /**
     * get the singleton instance
     *
     * @return
     */
    public static ReadEventHandler getInstance() {
        return instance;
    }

    @Override
    public void handle(ReadEvent event) {
        SelectionKey key = event.getKey();
        SocketChannel channel = (SocketChannel) key.channel();
        SocketSession session = (SocketSession) key.attachment();

        try {
            ChannelData channelData = session.getChannelData(channel);
            ByteBuffer readBuffer = channelData.getReadBuffer();
            int size = channel.read(readBuffer);
            if (size < 0) {
                session.close();
                return;
            }

            readBuffer.flip();
            handleRead(session, channelData.getType(), readBuffer);
        } catch (Exception e) {
            log.info("处理读取事件异常", e);
            session.close();
        }
    }

    /**
     * 处理读
     *
     * @param session
     * @param sourceType
     * @param readBuffer
     */
    private void handleRead(SocketSession session, ChannelType sourceType, ByteBuffer readBuffer) throws IOException {
        if (session.getStage().equals(SocketSession.Stage.TRANSFER)) {
            handleReadTransfer(session, sourceType, readBuffer);
            log.debug("transfer {} data", sourceType);
        } else {
            handleSocks5Read(session, sourceType, readBuffer);
        }
    }

    /**
     * 处理数据传送
     *
     * @param session
     * @param sourceType
     * @param readBuffer
     */
    private void handleReadTransfer(SocketSession session, ChannelType sourceType, ByteBuffer readBuffer) {
        byte[] bytes = new byte[readBuffer.remaining()];
        readBuffer.get(bytes).compact();
        addDataToSend(session, sourceType.getOpposite(), bytes, true);
    }

    /**
     * 处理socket连接事件
     * @param session
     * @param sourceType
     * @param readBuffer
     */
    private void handleSocks5Read(SocketSession session, ChannelType sourceType, ByteBuffer readBuffer) throws IOException {
        Socks5StageHandler stageHandler = Socks5HandlerFactory.getHandler(session.getStage());
        Socks5HandleResult skresult = stageHandler.handle(readBuffer);
        sendSocks5Response(session, skresult);

        if(skresult.getType() == Socks5HandleResult.Type.COMPLETED) {
            readBuffer.compact();
            log.debug("socks5 {} completed", session.getStage());
            session.setSocks5NextStage();
        } else if(skresult.getType() == Socks5HandleResult.Type.UNCOMPLETED) {
            readBuffer.position(readBuffer.limit());
            readBuffer.limit(readBuffer.capacity());
            log.debug("socks5 {} uncompleted", session.getStage());
        } else if(skresult.getType() == Socks5HandleResult.Type.ERROR) {
            log.error("socks5 {} error, close session", session.getStage());
            session.close();
        } else {
            throw new IllegalStateException("wrong code: " + skresult.getType());
        }
    }

    private void sendSocks5Response(SocketSession session, Socks5HandleResult handleResult) throws IOException {
        if(handleResult.getResponseToLocal() != null) {
            addDataToSend(session,ChannelType.LOCAL,handleResult.getResponseToLocal(), false);
        }

        if(handleResult.getResponseToRemote() != null) {
            if(!session.isRemoteConnected()) {
                session.connect2Remote();
            }
            addDataToSend(session, ChannelType.REMOTE, handleResult.getResponseToRemote(), true);
        }
    }

    /**
     * 转送数据到另一端
     *
     * @param session
     * @param targetType
     * @param originalData
     * @param needEncryption
     */
    private void addDataToSend(SocketSession session, ChannelType targetType, byte[] originalData, boolean needEncryption) {
        byte[] dataToSend = originalData;
        if (needEncryption) {
            dataToSend = handleDataEncryption(session.getEncryptor(), targetType, originalData);
        }
        ChannelData channelData = session.getChannelData(targetType);
        if (null == channelData) {
            throw new NullPointerException("找不到要传送数据的目标对象：" + targetType);
        }
        channelData.addDataToSend(dataToSend);
        JavaClientServer.getInstance().addEvent(new WriteEvent(session, targetType));
    }

    /**
     * 对传送的数据进行的必要的加解密操作
     *
     * @param encryptor
     * @param targetType
     * @param data
     * @return
     */
    private byte[] handleDataEncryption(Encryptor encryptor, ChannelType targetType, byte[] data) {
        byte[] handledData;
        if (targetType == ChannelType.REMOTE) {
            handledData = encryptor.encrypt(data);
        } else if (targetType == ChannelType.LOCAL) {
            handledData = encryptor.decrypt(data);
        } else {
            throw new IllegalStateException("wrong code: " + targetType);
        }
        return handledData;
    }
}
