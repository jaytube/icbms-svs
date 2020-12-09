package com.icbms.core.socket.handler;

import com.icbms.core.socket.model.TcpMessage;
import org.apache.mina.core.session.IoSession;


/**
 * @author Raymond
 * 
 */
public interface MessageManagerLogicHandler {

	void doExec(TcpMessage message, IoSession session);
}
