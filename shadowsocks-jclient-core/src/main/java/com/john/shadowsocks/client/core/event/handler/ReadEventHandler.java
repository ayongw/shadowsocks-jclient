package com.john.shadowsocks.client.core.event.handler;

import com.john.shadowsocks.client.core.event.data.ReadEvent;

/**
 * @author jiangguangtao on 2016/5/24.
 */
public class ReadEventHandler implements EventHandler<ReadEvent> {
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


    }
}
