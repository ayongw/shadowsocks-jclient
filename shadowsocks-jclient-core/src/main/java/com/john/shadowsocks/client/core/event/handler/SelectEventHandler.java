package com.john.shadowsocks.client.core.event.handler;

import com.john.shadowsocks.client.core.JavaClientServer;
import com.john.shadowsocks.client.core.event.data.AcceptEvent;
import com.john.shadowsocks.client.core.event.data.ReadEvent;
import com.john.shadowsocks.client.core.event.data.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * select event (loop event) handler
 *
 * @author jiangguangtao on 2016/5/24.
 */
public class SelectEventHandler implements EventHandler<SelectEvent> {
    private static final SelectEventHandler instance = new SelectEventHandler();
    private static final Logger log = LoggerFactory.getLogger(SelectEventHandler.class);

    private SelectEventHandler() {

    }

    /**
     * get the singleton instance
     *
     * @return
     */
    public static SelectEventHandler getInstance() {
        return instance;
    }

    @Override
    public void handle(SelectEvent event) {
        JavaClientServer clientServer = JavaClientServer.getInstance();
        Selector selector = clientServer.getSelector();

        try {
            int ready;//ready event count,,for OP_ACCEPT
            if (clientServer.isEventQueueEmpty()) {
                ready = selector.select();
            } else {
                ready = selector.selectNow();
            }

            if (ready > 0) {
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selKey = keyIterator.next();
                    handleSelectionKey(selKey);
                    keyIterator.remove();
                }
            }

        } catch (IOException e) {
            log.error("handle {} event error!", event.getType().toString(), e);
        } finally {
            clientServer.addEvent(new SelectEvent());
        }
    }

    /**
     * handle SelectionKey for OP_ACCEPT register
     *
     * @param key
     */
    private void handleSelectionKey(SelectionKey key) {
        if (!key.isValid()) {
            return; //invalid key ignore;
        }
        if (key.isAcceptable()) {
            JavaClientServer.getInstance().addEvent(new AcceptEvent());
        } else if (key.isReadable()) {
            JavaClientServer.getInstance().addEvent(new ReadEvent());
        }
    }
}
