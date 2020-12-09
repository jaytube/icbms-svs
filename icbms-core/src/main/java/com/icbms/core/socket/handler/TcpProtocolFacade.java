package com.icbms.core.socket.handler;

import com.icbms.core.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class TcpProtocolFacade implements MessageManagerFacade {

	private Map<Integer, MessageManagerLogicHandler> facadeMap = new HashMap<Integer, MessageManagerLogicHandler>();

	@Autowired
	private TcpMessageHandler tcpMessageHandler;

	@PostConstruct
	public void registry() {
		// // 透传客户端的MsgPack消息
		facadeMap.put(Constant.SHUANGRUI_CAIJIYI_TCP, tcpMessageHandler);
	}

	public Map<Integer, MessageManagerLogicHandler> getFacadeMap() {
		return facadeMap;
	}

}
