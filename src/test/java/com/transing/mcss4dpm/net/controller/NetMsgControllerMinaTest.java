package com.transing.mcss4dpm.net.controller;

import com.jeeframework.testframework.AbstractMinaBaseTest;
import com.transing.mcss4dpm.net.NetClientHandler;
import com.transing.mcss4dpm.net.protocol.MinaNetDataCodecFactory;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.junit.Test;

import java.net.ConnectException;
import java.net.InetSocketAddress;

/**
 * 网络层协议单元测试
 *
 * @author lance
 * @version 1.0 2017-02-24 13:35
 */
public class NetMsgControllerMinaTest extends AbstractMinaBaseTest {

    @Test
    public void testMinaNetData() throws Exception {
        SocketConnector socketConnector;

        /**
         * 缺省连接超时时间
         */
        final int DEFAULT_CONNECT_TIMEOUT = 5;

        final String HOST = "127.0.0.1";

        final int PORT = 5050;

        socketConnector = new NioSocketConnector();

        // 长连接
        // socketConnector.getSessionConfig().setKeepAlive(true);

        socketConnector.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);

        socketConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MinaNetDataCodecFactory()));

        NetClientHandler ioHandler = new NetClientHandler();
        socketConnector.setHandler(ioHandler);


        InetSocketAddress addr = new InetSocketAddress(HOST, PORT);
        ConnectFuture cf = socketConnector.connect(addr);
        try {
            cf.awaitUninterruptibly();


//            cf.getSession().write(msg);
//            System.out.println("send message " + msg);
            System.out.println("发送数据完成");


            IoSession session = cf.getSession();
            session.getConfig().setUseReadOperation(true);
            session.getCloseFuture().awaitUninterruptibly();
            System.out.println("After Writing");
        } catch (RuntimeIoException e) {
            if (e.getCause() instanceof ConnectException) {
                try {
                    if (cf.isConnected()) {
                        cf.getSession().close();
                    }
                } catch (RuntimeIoException e1) {
                }
            }
        }
    }

}