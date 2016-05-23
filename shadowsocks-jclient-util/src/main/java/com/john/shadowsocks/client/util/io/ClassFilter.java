package com.john.shadowsocks.client.util.io;

/**
 * 〈一句话功能简述〉<br/>
 * 〈扫描的类文件过滤器〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ClassFilter {
    boolean accept(Class clazz);
}
