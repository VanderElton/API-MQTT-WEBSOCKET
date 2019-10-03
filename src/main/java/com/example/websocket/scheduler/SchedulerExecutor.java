package com.example.websocket.scheduler;

import com.example.websocket.entity.scene.Action;
import com.example.websocket.mqtt.IotMqttDeviceManager;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class SchedulerExecutor extends QuartzJobBean {

    @Autowired
    private IotMqttDeviceManager iotMqttDeviceManager;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Action action = (Action) jobDataMap.get("action");
        iotMqttDeviceManager.publishScheduleCommand(action);
        System.out.println("Executed Job: " + jobExecutionContext.getJobDetail().getKey().toString());
    }

    // TODO: Schedule and run Jobs by Class Type.
    private void runJob(String job){
        if(job.equals("scene")){ }
        if(job.equals("command")){ }
    }
}
