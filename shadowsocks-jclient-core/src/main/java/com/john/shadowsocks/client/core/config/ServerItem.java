package com.john.shadowsocks.client.core.config;

import org.apache.commons.lang3.StringUtils;

/**
 * 〈一句话功能简述〉<br/>
 * 〈功能详细描述〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ServerItem {
    //服务器地址
    private String server;
    //端口
    private Integer serverPort;
    //连接密码
    private String password;
    //加密方式
    private String encryptMethod;
    //备注
    private String remarks;
    //是否激活
    private boolean active;

    public ServerItem() {
    }

    public ServerItem(String server, Integer serverPort, String password, String encryptMethod, String remarks) {
        this.server = server;
        this.serverPort = serverPort;
        this.password = password;
        this.encryptMethod = encryptMethod;
        this.remarks = remarks;
        this.active = false;
    }

    public ServerItem(String server, Integer serverPort, String password, String encryptMethod) {
        this.server = server;
        this.serverPort = serverPort;
        this.password = password;
        this.encryptMethod = encryptMethod;
        this.active = false;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptMethod() {
        return encryptMethod;
    }

    public void setEncryptMethod(String encryptMethod) {
        this.encryptMethod = encryptMethod;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * 获取此代理的key值，能够唯一标识此代理的字符串
     * <br/>ip:端口 为key
     *
     * @return
     */
    public String getRowKey() {
        return server + ":" + serverPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerItem that = (ServerItem) o;

        if (!server.equals(that.server)) return false;
        return serverPort.equals(that.serverPort);

    }

    @Override
    public int hashCode() {
        int result = server.hashCode();
        result = 31 * result + serverPort.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ServerItem{" +
                "remarks='" + remarks + '\'' +
                ", encryptMethod='" + encryptMethod + '\'' +
                ", password='" + password + '\'' +
                ", serverPort=" + serverPort +
                ", server='" + server + '\'' +
                '}';
    }
}
