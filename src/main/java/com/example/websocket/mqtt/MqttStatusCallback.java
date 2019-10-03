package com.example.websocket.mqtt;

import com.ibm.iotf.client.app.ApplicationStatus;
import com.ibm.iotf.client.app.DeviceStatus;
import com.ibm.iotf.client.app.StatusCallback;

import javax.websocket.Session;
import java.io.IOException;

public class MqttStatusCallback implements StatusCallback {

    private Session session;

    //private MessageRepository messageRepository;

    public MqttStatusCallback (Session session) {
        //this.messageRepository = SpringContext.getBean(MessageRepository.class);
        this.session = session;
    }
    @Override
    public void processApplicationStatus(ApplicationStatus applicationStatus) {

    }

    @Override
    public void processDeviceStatus(DeviceStatus deviceStatus) {
        System.out.println(deviceStatus.getDeviceId() + "--" + deviceStatus.getPayload());
        try {
            session.getBasicRemote().sendText(deviceStatus.getAction());
//            msgCallback.setStatus(deviceStatus.getAction());
//            if(!deviceStatus.getAction().equals("Connect")){
//                messageRepository.save(msgCallback);
//            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
