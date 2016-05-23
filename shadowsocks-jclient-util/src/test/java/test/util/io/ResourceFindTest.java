package test.util.io;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
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
public class ResourceFindTest {
    private static final Logger log = LoggerFactory.getLogger(ResourceFindTest.class);


    @Test
    public void test1() throws IOException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        String beginPath = "test/util";
//        String beginPath = "org/slf4j";
//        String beginPath = "sun/java";
        log.info("begin path:{}", beginPath);

        Enumeration<URL> resourceUrls = cl.getResources(beginPath);
        if(null == resourceUrls) {
            log.warn("未找到资源 ！！！");
            return;
        }
        Map<String, Class<?>> classMap = new HashMap<>();




        while (resourceUrls.hasMoreElements()) {
            URL url = resourceUrls.nextElement();
            log.info("资源 ：{}", url);
            String proto = url.getProtocol();
            File dir = new File(url.getPath().substring(1));
            doFindInFile(dir, classMap);
        }



    }

    /**
     * 在文件系统中查询
     * @param file
     * @param classMap
     */
    private void doFindInFile(File file, Map<String, Class<?>> classMap) {
        if(file.isFile()) {

        }
    }
}
