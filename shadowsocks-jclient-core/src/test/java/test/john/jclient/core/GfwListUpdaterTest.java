package test.john.jclient.core;

import com.john.shadowsocks.client.core.service.GfwListUpdater;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * 〈一句话功能简述〉<br/>
 * 〈功能详细描述〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class GfwListUpdaterTest {
    private static final Logger log = LoggerFactory.getLogger(GfwListUpdaterTest.class);

    @Test
    public void getLocalAddressTest() throws UnknownHostException {
        InetAddress listenAddress = InetAddress.getLocalHost();
        log.info("local host:{}", listenAddress.toString());
        listenAddress = InetAddress.getLoopbackAddress();
        log.info("loopback address:{}", listenAddress.toString());

        InetSocketAddress isa = new InetSocketAddress(1090);
        log.info("socket address :{}", isa);
        isa = new InetSocketAddress(InetAddress.getLoopbackAddress(), 1090);
        log.info("socket address :{}", isa);

    }

    @Test
    public void testGetGfw() throws Exception {
        GfwListUpdater updater = new GfwListUpdater();

        updater.updatePacFromGfwList();


    }
}
