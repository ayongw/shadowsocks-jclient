package com.john.shadowsocks.client.core.event.data;

/**
 * can read data event
 * @author jiangguangtao on 2016/5/24.
 */
public class ReadEvent implements Event{
    @Override
    public EventType getType() {
        return EventType.READ;
    }
}
