package com.john.shadowsocks.client.core.strategy;

import com.john.shadowsocks.client.core.config.ClientServerConfig;
import com.john.shadowsocks.client.core.config.ServerItem;
import com.sun.corba.se.spi.activation.Server;

import java.util.List;

/**
 *
 * @author jiangguangtao on 2016/5/24.
 */
public interface SwitchStrategy {
    String getId();
    String getName();

    /**
     * reload server configs
     * @param serverItemList
     */
    void reloadServers(List<ServerItem> serverItemList);

    /**
     *
     * @return
     */
    ServerItem getServer();
}
