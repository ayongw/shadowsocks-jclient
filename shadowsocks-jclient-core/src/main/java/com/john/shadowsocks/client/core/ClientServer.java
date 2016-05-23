package com.john.shadowsocks.client.core;

import com.john.shadowsocks.client.core.config.ClientServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * 〈一句话功能简述〉<br/>
 * 〈用于接收连接与转换数据到Shadowsocks服务器〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ClientServer {
    private static final Logger log = LoggerFactory.getLogger(ClientServer.class);

    public void startListener(ClientServerConfig config) throws IOException {
        Selector selector = SelectorProvider.provider().openSelector();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        InetSocketAddress listenAddress;
        if (config.isShareOverLan()) {
            listenAddress = new InetSocketAddress(config.getLocalPort());
        } else {
            listenAddress = new InetSocketAddress(InetAddress.getLoopbackAddress(), config.getLocalPort());
        }
//        ssc.bind(isa);
        // 给 ServerSocketChannel 对应的 socket 绑定 IP 和端口
        ssc.socket().setReuseAddress(true);
        ssc.socket().bind(listenAddress);
        log.info("开始监听 ：{}", listenAddress);

        //将 ServerSocketChannel 注册到 Selector 上，返回对应的 SelectionKey
//        int ops = SelectionKey.OP_ACCEPT|SelectionKey.OP_CONNECT|SelectionKey.OP_READ|SelectionKey.OP_WRITE;
        int ops = SelectionKey.OP_ACCEPT;
        SelectionKey selectionKey = ssc.register(selector, ops);

        while (selector.select() > 0) {
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selKey = keyIterator.next();
                keyIterator.remove();

                processConn((ServerSocketChannel) selKey.channel());
            }
        }


    }

    /**
     * 处理一个客户连接
     *
     * @param channel
     */
    private void processConn(ServerSocketChannel channel) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = channel.accept();
            log.debug("处理连接1：{}", socketChannel);
            log.debug("处理连接2：{}", socketChannel.getRemoteAddress());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            byte[] bytes;
            int size = 0;
            while ((size = socketChannel.read(buffer)) > 0) {
                buffer.flip();
                bytes = new byte[size];
                buffer.get(bytes);
                baos.write(bytes);
                buffer.clear();
            }
//            buffer.clear();
//            buffer.put("hello world!".getBytes(AppConst.DEFAULT_CHARSET));
//            buffer.flip();
//            socketChannel.write(buffer);
//            socketChannel.close();
            String str = new String(baos.toByteArray());

            log.info("接收到的信息是：\n{}", str);

        } catch (IOException e) {
            log.error("出错了", e);
        }


    }

}
