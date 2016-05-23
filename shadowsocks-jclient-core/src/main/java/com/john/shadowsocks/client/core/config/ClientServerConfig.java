package com.john.shadowsocks.client.core.config;

import com.john.shadowsocks.client.core.service.GfwListUpdater;
import com.john.shadowsocks.client.core.strategy.StrategyTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br/>
 * 〈shadowsocks客户端服务器配置信息〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ClientServerConfig {
    //shadowsocks运程服务器列表
    private List<ServerItem> configs;
    //服务器切换策略
    private String strategy;
    //允许来自局域网的连接
    private boolean shareOverLan;
    //本地端口
    private int localPort = 1080;
    // 远端gfw列表地址 不指定使用默认的github地址
    private String gfwListUrl;

    public ClientServerConfig() {
    }

    /**
     * 初始化一个新的配置信息
     * @return
     */
    public static ClientServerConfig initNew() {
        ClientServerConfig config = new ClientServerConfig();
        config.strategy = StrategyTypeEnum.STATIC.getKey();
        config.configs = new ArrayList<>(0);
        config.shareOverLan = false;
        config.localPort = 10801;
        config.gfwListUrl = GfwListUpdater.DEFAULT_GFW_LIST_URL;
        return config;
    }

    public List<ServerItem> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ServerItem> configs) {
        this.configs = configs;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public boolean isShareOverLan() {
        return shareOverLan;
    }

    public void setShareOverLan(boolean shareOverLan) {
        this.shareOverLan = shareOverLan;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public String getGfwListUrl() {
        return gfwListUrl;
    }

    public void setGfwListUrl(String gfwListUrl) {
        this.gfwListUrl = gfwListUrl;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "configs=" + configs +
                ", strategy='" + strategy + '\'' +
                ", shareOverLan=" + shareOverLan +
                ", localPort=" + localPort +
                ", gfwListUrl='" + gfwListUrl + '\'' +
                '}';
    }
}
