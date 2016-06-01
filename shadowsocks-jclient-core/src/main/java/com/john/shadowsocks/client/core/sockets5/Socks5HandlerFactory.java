package com.john.shadowsocks.client.core.sockets5;


import com.john.shadowsocks.client.core.session.SocketSession;

/**
 * @author fsneak
 */
public class Socks5HandlerFactory {
    /**
     * 根据socket连接的不同阶段获取相应的处理类
     * @param stage
     * @return
     */
    public static Socks5StageHandler getHandler(SocketSession.Stage stage) {
        switch (stage) {
            case SOCKS5_HELLO:
                return Socks5HelloHandler.getInstance();
            case SOCKS5_CONNECT:
                return Socks5CmdHandler.getInstance();
            default:
                throw new IllegalArgumentException("not socks5 stage: " + stage);
        }
    }
}
