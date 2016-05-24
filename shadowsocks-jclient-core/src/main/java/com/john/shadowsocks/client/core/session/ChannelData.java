package com.john.shadowsocks.client.core.session;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 *
 * @author jiangguangtao on 2016/5/24.
 */
public class ChannelData {
    private static final int BUFFER_SIZE = 32 * 1024;

    private final ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    private final LinkedList<ByteBuffer> dataQueue = new LinkedList<ByteBuffer>();
    private final SocketSession session;
    private final ChannelType type;
    private final SocketChannel channel;

    /**
     *
     * @param session socket session object
     * @param type socket channel type
     * @param socketChannel the socket channel
     */
    public ChannelData(SocketSession session, ChannelType type, SocketChannel socketChannel) {
        this.session = session;
        this.channel = socketChannel;
        this.type = type;
    }

    /**
     * read buffer of the socket channel
     * @return
     */
    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    /**
     *
     * @return channel type(local or remote)
     */
    public ChannelType getType() {
        return type;
    }

    /**
     *
     * @return related socket channel
     */
    public SocketChannel getChannel() {
        return channel;
    }

    /**
     * add data to send queue list (end)
     * @param data
     */
    public void addDataToSend(byte[] data) {
        dataQueue.add(ByteBuffer.wrap(data));
    }

    /**
     * get the send buffer from the send queue list (head)
     * @return
     */
    public ByteBuffer peekDataToSend() {
        return dataQueue.peek();
    }

    /**
     * get and remove the send buffer from the send queue list (head)
     * @return
     */
    public ByteBuffer pollDataToSend() {
        return dataQueue.poll();
    }
}
