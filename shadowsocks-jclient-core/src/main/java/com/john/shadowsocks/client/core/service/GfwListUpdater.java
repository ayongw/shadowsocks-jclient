package com.john.shadowsocks.client.core.service;

import com.john.shadowsocks.client.util.http.HtmlFetchUtil;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br/>
 * 〈GFW list更新服务〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class GfwListUpdater {
    private static final Logger log = LoggerFactory.getLogger(GfwListUpdater.class);
    //默认地址
    public static final String DEFAULT_GFW_LIST_URL = "https://raw.githubusercontent.com/gfwlist/gfwlist/master/gfwlist.txt";
    private String gfwListUrl = DEFAULT_GFW_LIST_URL;
    private String pacFileName;


    public String getGfwListUrl() {
        return gfwListUrl;
    }

    public void setGfwListUrl(String gfwListUrl) {
        this.gfwListUrl = gfwListUrl;
    }

    /**
     * 更新GFW
     */
    public void updatePacFromGfwList() throws Exception {
        String content = HtmlFetchUtil.getHtml(getGfwListUrl());

        writeGwf(content);
        List<String> ruleLines = parseResult(content);
    }

    public void writeGwf(String content) {

    }


    /**
     * 解析返回结果
     *
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    public List<String> parseResult(String response) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(response)) {
            return null;
        }
        byte[] decodeBytes = Base64.decodeBase64(response);
        String content = new String(decodeBytes, CharEncoding.US_ASCII);
        String[] arrs = content.split("(\r\n)|\n|\r");
        List<String> lines = new LinkedList<>();
        log.info("下载的规则：");
        for (String str : arrs) {
            if (StringUtils.isBlank(str)) {
                continue;
            }
            str = StringUtils.trimToEmpty(str);
            if (str.startsWith("!")
                    || str.startsWith("[")) {
                continue;
            }
            log.info(str);
            lines.add(str);
        }
        return lines;
    }
}
