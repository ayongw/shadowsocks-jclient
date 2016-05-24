package com.john.shadowsocks.client.core.event.data;

/**
 * 〈event type enum〉
 *
 * @author jiangguangtao on 2016/5/24.
 */
public enum EventType {
    /**
     * accept client connect
     */
    ACCEPT,
    /**
     * loop select event
     */
    SELECT,
    /**
     * socket readable
     */
    READ,
    /**
     * socket writable
     */
    WRITE;
}
