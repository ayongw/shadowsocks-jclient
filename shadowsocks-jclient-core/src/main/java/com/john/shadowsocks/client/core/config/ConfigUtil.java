package com.john.shadowsocks.client.core.config;

import com.john.shadowsocks.client.core.AppConst;
import com.john.shadowsocks.client.core.JavaClientServer;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br/>
 * 〈功能详细描述〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ConfigUtil {
    private static final Logger log = LoggerFactory.getLogger(ConfigUtil.class);

    /**
     * 加载配置文件信息
     * @return null 如果未能正确定加载（指定位置不存在配置信息）
     */
    public static ClientServerConfig loadConfig() {
        try {
            InputStream inputStream = ConfigUtil.class.getResourceAsStream(AppConst.CONFIG_FILE_NAME);
            if (null == inputStream) {
                log.warn("从类路径下未找到配置文件！");

                File userDir = new File(System.getProperty("user.dir"));
                File userDirFile = new File(userDir, AppConst.CONFIG_FILE_NAME);
                if (!userDirFile.exists()) {
                    log.warn("用户目录下无配置文件！{}", userDir.getAbsolutePath());
                    return null;
                }
                inputStream = new FileInputStream(userDirFile);
            }
            String configTxt = IOUtils.toString(inputStream, AppConst.DEFAULT_CHARSET);
            JSONObject jsonObject = JSONObject.fromObject(configTxt);
            Map classMap = new HashMap();
            classMap.put("configs", JavaClientServer.class);

            ClientServerConfig config = (ClientServerConfig) JSONObject.toBean(jsonObject, ClientServerConfig.class, classMap);
            return config;
        } catch (IOException e) {
            log.warn("读取配置流时异常！");
        }
        return null;
    }

    /**
     * 写配置文件
     *
     * @param config
     */
    public static void writeConfig(ClientServerConfig config) throws IOException {
        JSONObject jsonObject = JSONObject.fromObject(config);
        File userDir = new File(System.getProperty("user.dir"));
        File userDirFile = new File(userDir, AppConst.CONFIG_FILE_NAME);
        if (!userDirFile.exists()) {
            userDirFile.createNewFile();
        }

        String content = jsonObject.toString(2);
        FileOutputStream fos = new FileOutputStream(userDirFile);
        IOUtils.write(content, fos, AppConst.DEFAULT_CHARSET);
        IOUtils.closeQuietly(fos);
    }


}
