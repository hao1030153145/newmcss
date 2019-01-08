package com.transing.mcss4dpm.net.controller;

import com.jeeframework.logicframework.util.server.tcp.protocol.MinaProtocolHeader;
import com.jeeframework.testframework.AbstractMinaBaseTest;
import com.jeeframework.util.io.ByteStream;
import com.transing.mcss4dpm.net.controller.protocol.NetDataReq;
import com.transing.mcss4dpm.net.controller.protocol.NetDataResp;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * 网络层协议单元测试
 *
 * @author lance
 * @version 1.0 2017-02-24 13:35
 */
public class NetMsgControllerNIOTest extends AbstractMinaBaseTest {
    @Test
    public void testNIONetData() throws Exception {
        String ip = "127.0.0.1";
        int port = 5050;
        SocketChannel sock = null;
        try {
            sock = SocketChannel.open(new InetSocketAddress(ip, port));

            sock.socket().setSoLinger(true, 0);
            sock.socket().setReuseAddress(true);

            NetDataReq msg = new NetDataReq();
            msg.setDwUserUin(12341234);

            byte[] msgdata = new byte[msg.getSize() + MinaProtocolHeader.HEADER_LEN];
            ByteStream bs = new ByteStream();
            bs.resetBuffer(msgdata, msgdata.length);
            MinaProtocolHeader header = new MinaProtocolHeader();
            header.setCmdId(msg.getCmdId());
            header.setTotalLen(msgdata.length);
            header.setRetcode(msg.getResult());

            header.serialize(bs);

            msg.serialize(bs);
            ByteBuffer buf = ByteBuffer.allocate(msgdata.length);
            buf.put(msgdata);
            buf.flip();

            sock.write(buf);

            await().atMost(10, TimeUnit.SECONDS).until(didTheThing(sock));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sock.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static Callable<Boolean> didTheThing(final SocketChannel sock) {
        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                // 接收回包
                ByteBuffer responseHeaderBuf = ByteBuffer.allocate(MinaProtocolHeader.HEADER_LEN);
                while (sock.isConnected() && responseHeaderBuf.hasRemaining() && (sock.read(responseHeaderBuf) > 0)) {
                }
                responseHeaderBuf.flip();

                ByteStream headerBS = new ByteStream();
                headerBS.resetBuffer(responseHeaderBuf.array(), responseHeaderBuf.capacity());

                MinaProtocolHeader headerResp = new MinaProtocolHeader();
                headerResp.unSerialize(headerBS);

//            long cmdId = responseHeaderBuf.getInt();
                long msgDataTotalLen = headerResp.getTotalLen();
//            long retcode = responseHeaderBuf.getInt();


                int msgBodyLen = (int) (msgDataTotalLen - MinaProtocolHeader.HEADER_LEN);

                ByteBuffer body = ByteBuffer.allocate(msgBodyLen);
                while (sock.isConnected() && body.hasRemaining() && sock.read(body) > 0) {
                }
                body.flip();

                ByteStream responseBS = new ByteStream();
                responseBS.resetBuffer(body.array(), body.capacity());

                NetDataResp resp = new NetDataResp();
                resp.unSerialize(responseBS);
                System.out.println(" nio test     " + new String(resp.getSOutReserve()));
                return true;
            }
        };
    }

}