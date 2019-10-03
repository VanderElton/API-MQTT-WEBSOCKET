package com.example.websocket.mqtt;

import com.example.websocket.configuration.SpringContext;
import com.example.websocket.entity.MsgCallback;
import com.example.websocket.repository.MessageRepository;
import com.example.websocket.service.MessageService;
import com.ibm.iotf.client.app.Command;
import com.ibm.iotf.client.app.Event;
import com.ibm.iotf.client.app.EventCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class MqttEventScheduleCallback implements EventCallback {

    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    public MqttEventScheduleCallback() {
        this.messageRepository = SpringContext.getBean(MessageRepository.class);
    }

    @Override
    public void processEvent(Event e) {
        MsgCallback msgCallback = new MsgCallback();
        msgCallback.setPayload(e.getPayload());
        msgCallback.setEvent(e.getEvent());
        msgCallback.setDate(new SimpleDateFormat("HH:mm:ss.SSS").format(e.getTimestamp().toDate()));
        msgCallback.setDeviceId(e.getDeviceId() + " : " + e.getDeviceType() + " - " + "Scheduled");
        //update device iotF or cloudant
        messageRepository.save(msgCallback);
        messageService.saveEvent(msgCallback);
    }

    @Override
    public void processCommand(Command c) {

    }
}
