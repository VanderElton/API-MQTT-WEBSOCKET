package com.example.websocket.mqtt;

import com.example.websocket.configuration.SpringContext;
import com.example.websocket.entity.MsgCallback;
import com.example.websocket.repository.MessageRepository;
import com.example.websocket.service.MessageService;
import com.ibm.iotf.client.app.Command;
import com.ibm.iotf.client.app.Event;
import com.ibm.iotf.client.app.EventCallback;

import javax.websocket.Session;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class MqttEventCallback implements EventCallback {

    private Session session;

    private MessageRepository messageRepository;

    private MessageService messageService;

    public MqttEventCallback(Session session) {
        this.messageRepository = SpringContext.getBean(MessageRepository.class);
        this.messageService = SpringContext.getBean(MessageService.class);
        this.session = session;
    }

    @Override
    public void processEvent(Event e) {
        MsgCallback msgCallback = new MsgCallback();
        System.out.println(e.getDeviceId() + "--" + e.getPayload());
        try {
            session.getBasicRemote().sendText(e.getPayload());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        msgCallback.setPayload(e.getPayload());
        msgCallback.setEvent(e.getEvent());
        msgCallback.setDate(new SimpleDateFormat("HH:mm:ss.SSS").format(e.getTimestamp().toDate()));
        msgCallback.setDeviceId(e.getDeviceId() + " : " + e.getDeviceType());
        messageRepository.save(msgCallback);
        //messageService.save(msgCallback);
    }

    @Override
    public void processCommand(Command cmd) {

    }
}
