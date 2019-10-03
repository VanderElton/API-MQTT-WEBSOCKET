package com.example.websocket.mqtt;

import com.example.websocket.configuration.SpringContext;
import com.example.websocket.entity.CommandMqtt;
import com.example.websocket.entity.scene.Action;
import com.ibm.iotf.client.IoTFCReSTException;
import com.ibm.iotf.client.app.ApplicationClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.MqttException;

import javax.websocket.Session;

public class IotMqttDeviceManager {

    private static final Log LOGGER = LogFactory.getLog(IotMqttDeviceManager.class);

    public void pubSub(CommandMqtt commandMqtt, Session session){
        ApplicationClient applicationClient;
        try {
            applicationClient = openConnection();
            if(!session.getUserProperties().containsKey("CALLBACK")){
                applicationClient.setEventCallback(new MqttEventCallback(session));
                applicationClient.setStatusCallback(new MqttStatusCallback(session));
                session.getUserProperties().put("CALLBACK","");
            }
            applicationClient.subscribeToDeviceEvents(commandMqtt.getProductModel(),commandMqtt.getDeviceId());
            applicationClient.subscribeToDeviceStatus(commandMqtt.getProductModel(),commandMqtt.getDeviceId());
            applicationClient.publishCommand(commandMqtt.getProductModel(),commandMqtt.getDeviceId(),commandMqtt.getTopic(),commandMqtt.getCommand());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publishScheduleCommand(Action action){
        ApplicationClient applicationClient;
        try {
            applicationClient = openConnection();
            applicationClient.setEventCallback(new MqttEventScheduleCallback());
            applicationClient.subscribeToDeviceEvents(action.getProductModel(),action.getDeviceId());
            applicationClient.publishCommand(action.getProductModel(),action.getDeviceId(),action.getTopic().toString(),action.getCommand());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifyDeviceExist(String productModelId, String deviceId) throws IoTFCReSTException {
        ApplicationClient applicationClient = openConnection();
        return applicationClient.api().isDeviceExist(productModelId, deviceId);
    }

    private ApplicationClient openConnection() {
        ApplicationClient applicationClient = SpringContext.getBean(ApplicationClient.class);
        if (!applicationClient.isConnected()) {
            try {
                applicationClient.connect();
            } catch (MqttException ex) {
                LOGGER.error("Error to open MQTT connection.", ex);
            }
        }
        return applicationClient;
    }
}
