package com.nlte.RealTimePositioningServer;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NLTE on 2016/8/8
 */

public class RealTimePositionHandler extends IoHandlerAdapter {

    private List<IoSession> ioSessions = new ArrayList<>();

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);

        System.out.println(message.toString());
        for (IoSession ioSession : ioSessions) {
            if (!ioSession.equals(session)) {
                ioSession.write(message);
            }
        }
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        ioSessions.add(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        ioSessions.remove(session);
    }
}
