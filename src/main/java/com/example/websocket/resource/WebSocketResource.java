package com.example.websocket.resource;

import com.example.websocket.configuration.SpringContext;
import com.example.websocket.entity.CommandMqtt;
import com.example.websocket.mqtt.IotMqttDeviceManager;
import com.example.websocket.repository.CommandRepository;
import com.example.websocket.service.MessageService;
import com.ibm.iotf.client.IoTFCReSTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Controller
@ServerEndpoint(value = "/mqtt/{userId}/{deviceId}/{productModelId}/{topic}")
public class WebSocketResource {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketResource.class);

    private CommandMqtt commandMqtt = new CommandMqtt();

    private CommandRepository commandRepository;

    private IotMqttDeviceManager iotMqttDeviceManager;

    private MessageService messageService;

    public WebSocketResource(){
        this.commandRepository = SpringContext.getBean(CommandRepository.class);
        this.iotMqttDeviceManager = SpringContext.getBean(IotMqttDeviceManager.class);
        this.messageService = SpringContext.getBean(MessageService.class);
    }

    @OnOpen
    public void onOpen(Session session,
                       @PathParam("userId") String userId,
                       @PathParam("deviceId") String deviceId,
                       @PathParam("productModelId") String productModelId,
                       @PathParam("topic") String topic) throws IOException, IoTFCReSTException {
        LOGGER.info("onOpen " + session.getId());
        session.setMaxIdleTimeout(60000);
        session.getUserProperties().put("USERNAME", userId);
        sessions.add(session);
        if(!iotMqttDeviceManager.verifyDeviceExist(productModelId, deviceId)){
            session.getBasicRemote().sendText("Device not exist");
            onClose(session);
        }
        commandMqtt.setDeviceId(deviceId);
        commandMqtt.setProductModel(productModelId);
        commandMqtt.setTopic(topic);
    }

    @OnMessage
    public void onMessage(String payload, Session session) {
        try {
            LOGGER.info("onMessage From=" + session.getId());
            LOGGER.info("onMessage MsgCallback=" + payload);
            commandMqtt.setCommand(payload);
            iotMqttDeviceManager.pubSub(commandMqtt, session);
            commandMqtt.setOccurredAt(new SimpleDateFormat("HH:mm:ss.SSS").format(Date.from(Instant.now())));
            commandMqtt.setSession(session.getId());
            commandRepository.save(commandMqtt);
            messageService.saveCommand(commandMqtt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        sessions.remove(session);
        //session.close();
        LOGGER.info("onClose " + session.getId());
    }

    @OnError
    public void onError(Throwable t) {
        LOGGER.error(t.getMessage(), t);
    }
}
