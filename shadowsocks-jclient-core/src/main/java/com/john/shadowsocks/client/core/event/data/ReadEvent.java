package com.john.shadowsocks.client.core.event.data;

import java.nio.channels.SelectionKey;

/**
 * can read data event
 * @author jiangguangtao on 2016/5/24.
 */
public class ReadEvent implements Event{
    private final SelectionKey key;

    public ReadEvent(SelectionKey key) {
        this.key = key;
    }

    public SelectionKey getKey() {
        return key;
    }

    @Override
    public EventType getType() {
        return EventType.READ;
    }
}
