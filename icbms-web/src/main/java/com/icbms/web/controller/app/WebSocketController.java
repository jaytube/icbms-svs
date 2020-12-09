package com.icbms.web.controller.app;

import com.icbms.repository.domain.app.ClientMessage;
import com.icbms.repository.domain.app.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Controller
public class WebSocketController {

	private final static Logger logger = LoggerFactory.getLogger(WebSocketController.class);
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/sendTest")
	@SendTo("/topic/subscribeTest")
	public ServerMessage sendDemo(ClientMessage message) {
		logger.info("接收到了信息" + message.getName());
		return new ServerMessage("你发送的消息为:" + message.getName());
	}

	@SubscribeMapping("/subscribeTest")
	public ServerMessage sub() {
		return new ServerMessage("感谢你订阅了我11111。。。");
	}
}
