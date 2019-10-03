package com.example.websocket.configuration;

import com.cloudant.client.api.model.DbInfo;
import com.example.websocket.entity.CommandMqtt;
import com.example.websocket.entity.schedule.ActionSchedule;
import com.example.websocket.service.ScheduleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class InitializeSchedule {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    ScheduleService scheduleService;

    @PostConstruct
    private void initializeSchedule() throws IOException {
//        List<ActionSchedule> actions = scheduleService.find();
        DbInfo dbInfo = scheduleService.reinitializeDb();
//        for(ActionSchedule action: actions){
//            scheduleService.create(action);
//        }
    }
}
