package com.john.shadowsocks.client.core.strategy;

import com.john.shadowsocks.client.core.config.ClientServerConfig;
import com.john.shadowsocks.client.core.event.data.EventType;
import com.john.shadowsocks.client.core.event.handler.AcceptEventHandler;
import com.john.shadowsocks.client.core.event.handler.EventHandler;
import com.john.shadowsocks.client.core.event.handler.SelectEventHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jiangguangtao on 2016/5/24.
 */
public class SwitchStrategyFactory {
    /**
     * get the event handler to handle the type event
     *
     * @param config
     * @return server switch strategy
     */
    public static SwitchStrategy getStrategy(ClientServerConfig config) {
        if(StringUtils.equalsIgnoreCase(StrategyTypeEnum.STATIC.getKey(), config.getStrategy())) {
            return new StaticSwitchStrategy(config);
        }

        return null;
    }
}
