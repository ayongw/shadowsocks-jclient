package com.john.shadowsocks.client.core.session;

import com.john.shadowsocks.client.core.JavaClientServer;
import com.john.shadowsocks.client.core.codec.EncryptMethod;
import com.john.shadowsocks.client.core.codec.Encryptor;
import com.john.shadowsocks.client.core.config.ServerItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangguangtao on 2016/5/24.
 */
public class SocketSession {

    public enum Stage {
        SOCKS5_HELLO,
        SOCKS5_CONNECT,
        TRANSFER,
        CLOSE;
    }

    private static final Logger log = LoggerFactory.getLogger(SocketSession.class);
    private final Map<SocketChannel, ChannelData> channelMap = new HashMap<>(4);
    private Encryptor encryptor;
    private Stage stage;

    public SocketSession(SocketChannel localChannel) {
        ChannelData channelData = new ChannelData(this, ChannelType.LOCAL, localChannel);
        channelMap.put(localChannel, channelData);
        stage = Stage.SOCKS5_HELLO;
    }

    /**
     * 获取当前session的连接状态阶段
     */
    public Stage getStage() {
        return stage;
    }


    public void setSocks5NextStage() {
        if (stage == Stage.SOCKS5_HELLO) {
            stage = Stage.SOCKS5_CONNECT;
        } else if (stage == Stage.SOCKS5_CONNECT) {
            stage = Stage.TRANSFER;
        }
    }

    /**
     * get the channel data
     *
     * @param channel
     * @return
     */
    public ChannelData getChannelData(SocketChannel channel) {
        return channelMap.get(channel);
    }


    /**
     * get channel data for type
     *
     * @param type
     * @return
     */
    public ChannelData getChannelData(ChannelType type) {
        for (ChannelData channelData : channelMap.values()) {
            if (channelData.getType() == type) {
                return channelData;
            }
        }

        return null;
    }

    /**
     * is remote socket connected
     *
     * @return
     */
    public boolean isRemoteConnected() {
        return getChannelData(ChannelType.REMOTE) != null;
    }

    /**
     * connect to remote server for transfer local data
     */
    public void connect2Remote() throws IOException {
        if (isClosed()) {
            return;
        }

        for (ChannelData data : channelMap.values()) {
            if (data.getType() == ChannelType.REMOTE) {
                return;
            }
        }

        ServerItem serverItem = JavaClientServer.getInstance().getRemoteServer();

        SocketAddress address = new InetSocketAddress(serverItem.getServer(), serverItem.getServerPort());
        SocketChannel remoteChannel = SocketChannel.open(address);
        remoteChannel.configureBlocking(false);
        Selector selector = JavaClientServer.getInstance().getSelector();
        SelectionKey selectionKey = remoteChannel.register(selector, SelectionKey.OP_READ);
        selectionKey.attach(this);
        channelMap.put(remoteChannel, new ChannelData(this, ChannelType.REMOTE, remoteChannel));

        //FIXME 加密方法默认使用RC4-MD5
        encryptor = new Encryptor(serverItem.getPassword(), EncryptMethod.RC4_MD5);
    }

    /**
     * 获取数据传送加密、解密对象
     * <p>
     * <b>只有在连接远端服务器后才可用</b>
     * </p>
     *
     * @return
     */
    public Encryptor getEncryptor() {
        if (!isRemoteConnected()) {
            throw new RuntimeException("远程服务器未连接，不能获取加解密对象！");
        }
        return encryptor;
    }

    /**
     * close read
     */
    public void close() {
        for (SocketChannel socketChannel : channelMap.keySet()) {
            try {
                socketChannel.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        stage = Stage.CLOSE;
        channelMap.clear();
    }

    /**
     * 当前session是否关闭了
     *
     * @return
     */
    public boolean isClosed() {
        return stage == Stage.CLOSE;
    }

}
