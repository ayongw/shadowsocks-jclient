package com.john.shadowsocks.client.core.event.handler;

import com.john.shadowsocks.client.core.JavaClientServer;
import com.john.shadowsocks.client.core.event.data.AcceptEvent;
import com.john.shadowsocks.client.core.session.SocketSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * accept connect
 *
 * @author jiangguangtao on 2016/5/24.
 */
public class AcceptEventHandler implements EventHandler<AcceptEvent> {
    private static final AcceptEventHandler instance = new AcceptEventHandler();
    private static final Logger log = LoggerFactory.getLogger(AcceptEventHandler.class);

    private AcceptEventHandler() {

    }

    /**
     * get the singleton instance
     *
     * @return
     */
    public static AcceptEventHandler getInstance() {
        return instance;
    }

    @Override
    public void handle(AcceptEvent event) {
        ServerSocketChannel localServerAcceptor = JavaClientServer.getInstance().getLocalServerAcceptor();
        Selector selector = JavaClientServer.getInstance().getSelector();

        try {
            SocketChannel localChannel = localServerAcceptor.accept();
            if (localChannel != null) {
                localChannel.configureBlocking(false);
                SelectionKey key = localChannel.register(selector, SelectionKey.OP_READ);
                key.attach(new SocketSession(localChannel));
            }

        } catch (Exception e) {
            log.error("handle {} event error !", event.getType().toString(), e);
        }

    }
}
