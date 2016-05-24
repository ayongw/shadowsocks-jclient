package com.john.shadowsocks.client.core.strategy;

import com.john.shadowsocks.client.core.config.ClientServerConfig;
import com.john.shadowsocks.client.core.config.ServerItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author jiangguangtao on 2016/5/24.
 */
public class StaticSwitchStrategy implements SwitchStrategy {
    private static final Logger log = LoggerFactory.getLogger(StaticSwitchStrategy.class);
    private ClientServerConfig config;
    private ServerItem serverItem;

    public StaticSwitchStrategy(ClientServerConfig config) {
        this.config = config;
        reloadServers(config.getConfigs());
    }

    @Override
    public String getId() {
        return "staticStrategy";
    }

    @Override
    public String getName() {
        return "静态指定";
    }

    @Override
    public void reloadServers(List<ServerItem> serverItemList) {
        serverItem = null;
        if (null == config || null == config.getConfigs() || config.getConfigs().size() == 0) {
            log.warn("wrong config, no server list!");
            return;
        }

        for (ServerItem si : config.getConfigs()) {
            if (si.isActive()) {
                serverItem = si;
            }
        }

        if(null == serverItem) {
            serverItem = config.getConfigs().get(0);
        }
    }

    @Override
    public ServerItem getServer() {
        return serverItem;
    }
}
