package com.john.shadowsocks.client.core.event.handler;

import com.john.shadowsocks.client.core.JavaClientServer;
import com.john.shadowsocks.client.core.event.data.AcceptEvent;
import com.john.shadowsocks.client.core.event.data.WriteEvent;
import com.john.shadowsocks.client.core.session.ChannelData;
import com.john.shadowsocks.client.core.session.ChannelType;
import com.john.shadowsocks.client.core.session.SocketSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * write data to socket
 *
 * @author jiangguangtao on 2016/5/24.
 */
public class WriteEventHandler implements EventHandler<WriteEvent> {
    private static final WriteEventHandler instance = new WriteEventHandler();
    private static final Logger log = LoggerFactory.getLogger(WriteEventHandler.class);

    private WriteEventHandler() {

    }

    /**
     * get the singleton instance
     *
     * @return
     */
    public static WriteEventHandler getInstance() {
        return instance;
    }

    @Override
    public void handle(WriteEvent event) {
        SocketSession session = event.getSession();
        if(session.isClosed()) {
            return;
        }
        ChannelType channelType = event.getChannelType();
        ChannelData channelData = session.getChannelData(channelType);
        SocketChannel channel = channelData.getChannel();
        ByteBuffer buffer;

        try {
            while ((buffer = channelData.peekDataToSend())!= null) {
                channel.write(buffer);
                if(buffer.hasRemaining()) {
                    JavaClientServer.getInstance().addEvent(new WriteEvent(session, channelType));
                    break;
                } else {
                    channelData.pollDataToSend();
                }
            }
        } catch (Exception e) {
            log.error("handle {} event error !", event.getType().toString(), e);
            session.close();
        }

    }
}
