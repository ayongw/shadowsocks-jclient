package com.john.shadowsocks.client.core.event.data;

import com.john.shadowsocks.client.core.session.ChannelType;
import com.john.shadowsocks.client.core.session.SocketSession;

/**
 * can write data event
 *
 * @author jiangguangtao on 2016/5/24.
 */
public class WriteEvent implements Event {
    private final SocketSession session;
    private final ChannelType channelType;

    public WriteEvent(SocketSession session, ChannelType channelType) {
        this.channelType = channelType;
        this.session = session;
    }

    /**
     * 写事件关联的Session
     *
     * @return
     */
    public SocketSession getSession() {
        return session;
    }

    /**
     * 写事件目标socket类型（本地或者是远程）
     *
     * @return
     */
    public ChannelType getChannelType() {
        return channelType;
    }

    @Override
    public EventType getType() {
        return EventType.READ;
    }
}
