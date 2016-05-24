package com.john.shadowsocks.client.core;

import com.john.shadowsocks.client.core.codec.Encryptor;
import com.john.shadowsocks.client.core.config.ClientServerConfig;
import com.john.shadowsocks.client.core.config.ServerItem;
import com.john.shadowsocks.client.core.event.EventHandlerFactory;
import com.john.shadowsocks.client.core.event.data.Event;
import com.john.shadowsocks.client.core.event.data.EventQueue;
import com.john.shadowsocks.client.core.event.data.SelectEvent;
import com.john.shadowsocks.client.core.event.handler.EventHandler;
import com.john.shadowsocks.client.core.strategy.SwitchStrategy;
import com.john.shadowsocks.client.core.strategy.SwitchStrategyFactory;
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
import java.util.Iterator;

/**
 * 〈一句话功能简述〉<br/>
 * 〈用于接收连接与转换数据到Shadowsocks服务器〉
 *
 * @author jiangguangtao on 2016/5/23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class JavaClientServer {
    private static final Logger log = LoggerFactory.getLogger(JavaClientServer.class);
    private static JavaClientServer instance;
    private ServerSocketChannel localServerAcceptor;
    private Selector selector;
    private EventQueue eventQueue;
    private ClientServerConfig config;
    private boolean runningFlag;
    private Encryptor encryptor;
    private SwitchStrategy switchStrategy;
    /**
     * new a server
     *
     * @param config
     */
    private JavaClientServer(ClientServerConfig config) {
        eventQueue = new EventQueue();
        this.config = config;
        runningFlag = false;
        switchStrategy = SwitchStrategyFactory.getStrategy(config);
    }

    /**
     * init create a client server
     *
     * @param config
     */
    public static void init(ClientServerConfig config) {
        if (null != JavaClientServer.instance) {
            try {
                JavaClientServer.instance.stopListener();
            } catch (IOException e) {
                log.error("stop client server error!", e);
            }
        }
        JavaClientServer.instance = new JavaClientServer(config);
    }

    /**
     * get the singleton instance
     * @return
     */
    public static JavaClientServer getInstance() {
        return instance;
    }

    /**
     * get local socket acceptor
     *
     * @return
     */
    public ServerSocketChannel getLocalServerAcceptor() {
        return localServerAcceptor;
    }

    /**
     * get the server associated socket selector
     *
     * @return
     */
    public Selector getSelector() {
        return selector;
    }

    /**
     * get current server config
     *
     * @return
     */
    public ClientServerConfig getConfig() {
        return config;
    }

    /**
     * add an event to server event queue
     *
     * @param event
     */
    public void addEvent(Event event) {
        this.eventQueue.add(event);
    }

    /**
     * is the eventQueue empty
     *
     * @return
     */
    public boolean isEventQueueEmpty() {
        return eventQueue.isEmpty();
    }

    /**
     * get one remote server for transfer data
     * @return
     */
    public ServerItem getRemoteServer() {
        return switchStrategy.getServer();
    }

    public void startListener() throws IOException {
        selector = Selector.open();
        localServerAcceptor = ServerSocketChannel.open();
        localServerAcceptor.configureBlocking(false);

        InetSocketAddress listenAddress;
        if (config.isShareOverLan()) {
            listenAddress = new InetSocketAddress(config.getLocalPort());
        } else {
            listenAddress = new InetSocketAddress(InetAddress.getLoopbackAddress(), config.getLocalPort());
        }

        localServerAcceptor.socket().setReuseAddress(true);
        localServerAcceptor.socket().bind(listenAddress);
        log.info("begin listen on address：{}", listenAddress);

//        int ops = SelectionKey.OP_ACCEPT|SelectionKey.OP_CONNECT|SelectionKey.OP_READ|SelectionKey.OP_WRITE;
        localServerAcceptor.register(selector, SelectionKey.OP_ACCEPT);

        addEvent(new SelectEvent());
        runningFlag = true;

        while (runningFlag) {
            Event event = eventQueue.poll();
            if(null == event) {
                log.warn("event queue is empty!!!");
                continue;
            }

            log.debug("handle {} event.", event.getType().toString());

            try {
                EventHandler handler = EventHandlerFactory.getHandler(event.getType());
                handler.handle(event);
            } catch (Exception e) {
                log.error("handle {} event error!",event.getType().toString(), e);
            }
        }

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
     * stop server
     * @throws IOException
     */
    public void stopListener() throws IOException {
        runningFlag = false;
        localServerAcceptor.close();
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
            int size = 0;
            while ((size = socketChannel.read(buffer)) > 0) {
                buffer.flip();
                byte[] bytes = new byte[size];
                buffer.get(bytes);
                baos.write(bytes);
                buffer.clear();
            }

            String str = new String(baos.toByteArray());

            log.info("接收到的信息是：\n{}", str);

        } catch (IOException e) {
            log.error("出错了", e);
        }


    }

}
