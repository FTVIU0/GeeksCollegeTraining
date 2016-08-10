package com.nlte.RealTimePositioningServer;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class Main {

    public static void main(String[] args) {

        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        acceptor.getFilterChain().addLast("RealTimePositionCodec",
                new ProtocolCodecFilter(
                        new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.setHandler(new RealTimePositionHandler());

        try {
            acceptor.bind(new InetSocketAddress(9123));
            System.out.println("Bind 9123");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
