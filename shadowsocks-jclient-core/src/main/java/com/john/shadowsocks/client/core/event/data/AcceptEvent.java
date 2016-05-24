package com.john.shadowsocks.client.core.event.data;

/**
 * accept client connect event
 * @author jiangguangtao on 2016/5/24.
 */
public class AcceptEvent  implements Event{
    @Override
    public EventType getType() {
        return EventType.ACCEPT;
    }
}
