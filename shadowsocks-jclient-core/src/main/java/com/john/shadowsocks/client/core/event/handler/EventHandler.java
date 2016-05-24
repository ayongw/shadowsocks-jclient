package com.john.shadowsocks.client.core.event.handler;

import com.john.shadowsocks.client.core.event.data.Event;

/**
 * @author jiangguangtao on 2016/5/24.
 */
public interface EventHandler<T extends Event> {
    /**
     * handle event
     *
     * @param event
     */
    void handle(T event);
}
