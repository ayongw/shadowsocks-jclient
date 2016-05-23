package test.john.jclient.core;

import com.john.shadowsocks.client.core.service.GfwListUpdater;
import org.junit.Test;

/**
 * 〈一句话功能简述〉<br/>
 * 〈功能详细描述〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class GfwListUpdaterTest {

    @Test
    public void testGetGfw() throws Exception {
        GfwListUpdater updater = new GfwListUpdater();

        updater.updatePacFromGfwList();


    }
}
