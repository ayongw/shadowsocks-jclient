package test.util.io;

import com.john.shadowsocks.client.util.io.ClassFilter;
import com.john.shadowsocks.client.util.io.ClassFindUtil;
import org.junit.Before;
import org.junit.Test;
import test.util.io.mockdata.A;

import java.lang.reflect.Modifier;

/**
 * 〈一句话功能简述〉<br/>
 * 〈功能详细描述〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ClassFindUtilTest {
    private ClassFilter filter;

    @Before
    public void setUp() {
        filter = new ClassFilter() {
            @Override
            public boolean accept(Class clazz) {
                return
                        !Modifier.isAbstract(clazz.getModifiers())
                                && !Modifier.isInterface(clazz.getModifiers())
                                && Modifier.isPublic(clazz.getModifiers())
                                && !Modifier.isStatic(clazz.getModifiers())
                                && A.class.isAssignableFrom(clazz);
            }
        };
    }

    @Test
    public void testScanClassTest() throws Exception {
        for (Class clazz : ClassFindUtil.scanPackage("test.util", filter)) {
            System.out.println(clazz);
        }

    }
}
