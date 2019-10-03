package com.example.websocket.entity.scene;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.Map;

public class Action {

    @NotNull
    private String deviceId;
    @NotNull
    private String productModel;
    private String name;
    @NotNull
    private Topic topic;
    private Object command;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public Object getCommand() {
        return command;
    }

    public void setCommand(Object command) {
        this.command = command;
    }

}
