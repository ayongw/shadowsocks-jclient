package com.john.shadowsocks.client.core.event.data;

/**
 * select event(or loop event)
 * @author jiangguangtao on 2016/5/24.
 */
public class SelectEvent implements Event{
    @Override
    public EventType getType() {
        return EventType.SELECT;
    }
}
