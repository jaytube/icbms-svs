package com.icbms.core.socket.handler;

import com.icbms.core.socket.codec.MessageTcpDecoder;
import com.icbms.core.socket.codec.MessageTcpEncoder;
import com.icbms.core.socket.handler.biz.DataParamsResponseHandler;
import com.icbms.core.socket.model.LoraTcpMessage;
import com.icbms.core.util.TcpNetCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

@Component
public class TcpMessageFacade implements MessageManagerFacade {
    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Map<Integer, MessageManagerLogicHandler> facadeMap = new HashMap<Integer, MessageManagerLogicHandler>();
    
    @Resource(type = DataParamsResponseHandler.class)
    private DataParamsResponseHandler dataParamsResponseHandler;



    @PostConstruct
    public void registry() {
        logger.info("====================TcpMessageFacade Registry=======================");
        //注册解码器及编码器
        MessageCodecRegister.addEncoder(LoraTcpMessage.class, new MessageTcpEncoder());
        MessageCodecRegister.addDecoder(LoraTcpMessage.class, new MessageTcpDecoder());
        // TCP协议解析组件
        this.facadeMap.put(TcpNetCmd.GET_DATA_FROM_SERVER_ID, dataParamsResponseHandler);//前置机响应服务器读取门限参数
    }

    @Override
    public Map<Integer, MessageManagerLogicHandler> getFacadeMap() {
        return facadeMap;
    }

}
