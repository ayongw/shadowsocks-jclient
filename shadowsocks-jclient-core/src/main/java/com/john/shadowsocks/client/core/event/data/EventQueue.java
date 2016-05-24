package com.john.shadowsocks.client.core.event.data;

import java.util.LinkedList;

/**
 * event container
 *
 * @author jiangguangtao on 2016/5/24.
 */
public class EventQueue {
    private final LinkedList<Event> list = new LinkedList<Event>();

    /**
     * add new event to the end of queue
     * @param event
     */
    public void add(Event event) {
        list.add(event);
    }

    /**
     * Retrieves and removes the head (first element) of this list.
     * @return
     */
    public Event poll() {
        return list.poll();
    }

    /**
     * Returns true if there is no event.
     * @return
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
