package com.john.shadowsocks.client.core;

import org.apache.commons.codec.CharEncoding;

import java.nio.charset.Charset;

/**
 * 〈一句话功能简述〉<br/>
 * 〈功能详细描述〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface AppConst {
    Charset DEFAULT_CHARSET = Charset.forName(CharEncoding.UTF_8);

    /**
     * 配置文件名称
     */
    String CONFIG_FILE_NAME = "config.json";

    /**
     * 本地pac文件名称名称
     */
    String PAC_FILE_NAME = "pac.txt";


}
