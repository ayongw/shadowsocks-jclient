package com.john.shadowsocks.client.core.session;

/**
 * socket channel type
 * @author jiangguangtao on 2016/5/24.
 */
public enum ChannelType {
    LOCAL,
    REMOTE;

    /**
     * get the opposite type
     * @return
     */
    public ChannelType getOpposite() {
        if (this == LOCAL) {
            return REMOTE;
        } else if (this == REMOTE) {
            return LOCAL;
        } else {
            throw new IllegalStateException("wrong code: " + this);
        }
    }
}