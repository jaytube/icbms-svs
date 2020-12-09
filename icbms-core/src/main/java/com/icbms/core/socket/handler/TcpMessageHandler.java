package com.icbms.core.socket.handler;

import com.icbms.core.socket.model.TcpMessage;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;

@Component
public class TcpMessageHandler implements MessageManagerLogicHandler {

	private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Resource(type = TcpMessageFacade.class)
	private TcpMessageFacade facade;
	
	public void doExec(TcpMessage message, IoSession session) {
		handleMsgPack(message, session); 
	}
	
	public void handleMsgPack(TcpMessage message, IoSession session){
		if(facade.getFacadeMap() != null){
			MessageManagerLogicHandler handler = (MessageManagerLogicHandler) facade.getFacadeMap().get(message.getCmd());
			if(handler != null){
				handler.doExec(message, session);
			}
		}
	}
}
