package test.john.jclient.core;

import com.john.shadowsocks.client.core.config.ClientServerConfig;
import com.john.shadowsocks.client.core.config.ConfigUtil;
import org.junit.Test;

import javax.security.auth.login.Configuration;
import java.io.IOException;

/**
 * 〈一句话功能简述〉<br/>
 * 〈功能详细描述〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class JavaClientServerConfigTest {

    @Test
    public void testInitFile() throws IOException {
        ClientServerConfig config = ClientServerConfig.initNew();
        ConfigUtil.writeConfig(config);
    }
}
