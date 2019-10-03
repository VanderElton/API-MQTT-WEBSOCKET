package com.example.websocket.entity.schedule;

import com.cloudant.client.api.model.Document;

import java.util.HashSet;
import java.util.Set;

public class DocSchedule extends Document {

    private Set<ActionSchedule> actionSchedule;

    public DocSchedule(String id) {
        super();
        this.setId(id);
        this.actionSchedule = new HashSet<>();
    }

    public Set<ActionSchedule> getActionSchedule() {
        return actionSchedule;
    }

    public void setActionSchedule(Set<ActionSchedule> actionSchedule) {
        this.actionSchedule = actionSchedule;
    }
}
