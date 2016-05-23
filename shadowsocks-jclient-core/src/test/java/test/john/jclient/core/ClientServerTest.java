package test.john.jclient.core;

import com.john.shadowsocks.client.core.ClientServer;
import com.john.shadowsocks.client.core.config.ClientServerConfig;
import com.john.shadowsocks.client.core.config.ConfigUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 〈一句话功能简述〉<br/>
 * 〈功能详细描述〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ClientServerTest {
    private static final Logger log = LoggerFactory.getLogger(ClientServerTest.class);
    @Test
    public void testListen() throws IOException {
        ClientServerConfig config =  ConfigUtil.loadConfig();
        if(null == config) {
            config = ClientServerConfig.initNew();
        }

        ClientServer clientServer = new ClientServer();
        clientServer.startListener(config);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        log.info("按回车键结束程序 ");
        reader.readLine();
    }
}
