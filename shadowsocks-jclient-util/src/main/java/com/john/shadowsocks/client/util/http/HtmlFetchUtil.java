package com.john.shadowsocks.client.util.http;

import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 〈一句话功能简述〉<br/>
 * 〈功能详细描述〉
 *
 * @author jiangguangtao on 2016/4/28.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class HtmlFetchUtil {
    private static final Logger log = LoggerFactory.getLogger(HtmlFetchUtil.class);
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
    public static final int DEFAULT_TIMEOUT = 2000;

    /**
     * 获取HTMl内容
     *
     * @param url 从哪儿获取HTML
     * @return
     */
    public static String getHtml(String url) {
        return getHtml(url, DEFAULT_TIMEOUT, USER_AGENT);
    }

    /**
     * 获取HTMl内容
     *
     * @param url       从哪儿获取HTML
     * @param timeout   连接超时时间
     * @param userAgent 连接User-Agent
     * @return
     */
    private static String getHtml(String url, int timeout, String userAgent) {
        try {
            String content = Request
                    .Get(url)
                    .userAgent(userAgent)
                    .connectTimeout(timeout)
                    .execute().returnContent().asString();
            log.info("成功从{}获取内容", url);
//            log.info("content from {} is:\n{}", url, content);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
