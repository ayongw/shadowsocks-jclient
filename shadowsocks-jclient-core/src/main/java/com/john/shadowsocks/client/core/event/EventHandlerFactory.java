package com.john.shadowsocks.client.core.event;

import com.john.shadowsocks.client.core.event.data.EventType;
import com.john.shadowsocks.client.core.event.handler.AcceptEventHandler;
import com.john.shadowsocks.client.core.event.handler.EventHandler;
import com.john.shadowsocks.client.core.event.handler.SelectEventHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br/>
 * 〈功能详细描述〉
 *
 * @author jiangguangtao on 2016/5/24.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EventHandlerFactory {
    private static final Map<EventType, EventHandler> eventHandlerMap = new HashMap<>(5);

    static {
        registerHandler(EventType.ACCEPT, AcceptEventHandler.getInstance());
        registerHandler(EventType.SELECT, SelectEventHandler.getInstance());
    }

    /**
     * register eventType handler
     *
     * @param type
     * @param handler
     */
    public static void registerHandler(EventType type, EventHandler handler) {
        eventHandlerMap.put(type, handler);
    }

    /**
     * get the event handler to handle the type event
     *
     * @param type
     * @return eventHandler or UnsupportedOperationException
     * @throws UnsupportedOperationException if no handler for type
     */
    public static EventHandler getHandler(EventType type) {
        EventHandler handler = eventHandlerMap.get(type);
        if (null == handler) {
            throw new UnsupportedOperationException("type " + type);
        }
        return handler;
    }
}
