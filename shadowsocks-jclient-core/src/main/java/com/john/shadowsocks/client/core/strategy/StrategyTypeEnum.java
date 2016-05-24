package com.john.shadowsocks.client.core.strategy;

/**
 * 〈一句话功能简述〉<br/>
 * 〈功能详细描述〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum StrategyTypeEnum {
    //静态选择
    STATIC("static", "静态指定");

    private String key;
    private String text;

    StrategyTypeEnum(String name, String text) {
        this.key = name;
        this.text = text;
    }

    /**
     *
     * @param key
     * @return
     */
    public static StrategyTypeEnum getFromKey(String key) {
        for(StrategyTypeEnum ste : values()) {
            if(ste.key.equalsIgnoreCase(key)) {
                return ste;
            }
        }
        return null;
    }

    /**
     * 策略key值
     *
     * @return
     */
    public String getKey() {
        return key;
    }

    /**
     * 策略描述文本
     * @return
     */
    public String getText() {
        return text;
    }
}
