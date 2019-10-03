package com.example.websocket.entity.schedule;

import com.cloudant.client.api.model.Document;
import com.example.websocket.entity.scene.Action;

public class ActionSchedule extends Document {

    private Action action;
    private String dateTime;
    private String timeZone;


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
