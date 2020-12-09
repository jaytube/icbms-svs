package com.icbms.core.socket.handler;

import java.util.Map;

/**
 * @author Raymond
 * 
 */
public interface MessageManagerFacade {
	Map<Integer, MessageManagerLogicHandler> getFacadeMap();
}
