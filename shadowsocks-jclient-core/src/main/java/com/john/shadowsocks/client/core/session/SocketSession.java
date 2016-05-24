package com.john.shadowsocks.client.core.session;

import com.john.shadowsocks.client.core.JavaClientServer;
import com.john.shadowsocks.client.core.codec.Encryptor;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jiangguangtao on 2016/5/24.
 */
public class SocketSession {
    private final Map<SocketChannel, ChannelData> channelMap = new HashMap<>(4);
    private Encryptor encryptor ;

    public SocketSession(SocketChannel localChannel) {
        ChannelData channelData = new ChannelData(this, ChannelType.LOCAL, localChannel);
        channelMap.put(localChannel, channelData);
    }

    /**
     * get the channel data
     * @param channel
     * @return
     */
    public ChannelData getChannelData(SocketChannel channel) {
        return channelMap.get(channel);
    }


    /**
     * get channel data for type
     * @param type
     * @return
     */
    public ChannelData getChannelData(ChannelType type) {
        for (ChannelData channelData : channelMap.values()) {
            if (channelData.getType() == type) {
                return channelData;
            }
        }

        return null;
    }

    /**
     * is remote socket connected
     * @return
     */
    public boolean isRemoteConnected() {
        return getChannelData(ChannelType.REMOTE) != null;
    }

    /**
     * connect to remote server for transfer local data
     */
    public void connect2Remote() {

    }



}
