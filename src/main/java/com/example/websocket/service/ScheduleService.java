package com.example.websocket.service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.DbInfo;
import com.cloudant.client.api.model.Response;
import com.example.websocket.entity.scene.Action;
import com.example.websocket.entity.schedule.ActionSchedule;
import com.example.websocket.entity.schedule.DocSchedule;
import com.example.websocket.entity.schedule.ScheduleResponse;
import com.example.websocket.scheduler.SchedulerExecutor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class ScheduleService {

    protected static final Log LOGGER = LogFactory.getLog(ScheduleService.class);

    @Autowired
    private CloudantClient client;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private DocScheduleService docScheduleService;

    public Map<String, Boolean> scheduleScene(String homeId, String sceneId, String dateTime, String zoneId){
        Map<String, Boolean> success = new HashMap<>();
        for(Action action : sceneService.findById(homeId, sceneId).getActions()){
            ActionSchedule actionSchedule = new ActionSchedule();
            actionSchedule.setAction(action);
            actionSchedule.setDateTime(dateTime);
            actionSchedule.setTimeZone(zoneId);
            success.put(action.getDeviceId(),create(actionSchedule).isSuccess());
        }
        return success;
    }

    public ScheduleResponse create(ActionSchedule actionSchedule){
        ScheduleResponse scheduleResponse;
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.parse(actionSchedule.getDateTime()), ZoneId.of(actionSchedule.getTimeZone()));
            if(!dateTime.isBefore(ZonedDateTime.now())) {
                JobDetail jobDetail = buildJobDetail(actionSchedule.getAction());
                Trigger trigger = buildJobTrigger(jobDetail, dateTime);
                scheduler.scheduleJob(jobDetail, trigger);
                actionSchedule.setId(jobDetail.getKey().toString());
                if(saveAction(actionSchedule, dateTime).getStatusCode() == 201){
                    scheduleResponse = new ScheduleResponse(true, jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Action Scheduled Successfully!");
                }else{
                    scheduleResponse = new ScheduleResponse(false, "Error scheduling action. Please try later!");
                }
            }else{
                scheduleResponse = new ScheduleResponse(false, "DateTime must be after current time");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            scheduleResponse = new ScheduleResponse(false, "Error scheduling action. Please try later!");
        }
        return scheduleResponse;
    }

    public List<String> findAll(){
        List<String> currentlyExecutingJobs = new ArrayList<>();
        try {
            for(String group : scheduler.getJobGroupNames()){
                for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))){
                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();
                    //List<Trigger> triggers = (List<Trigger>)scheduler.getTriggersOfJob(jobKey);
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();
                    currentlyExecutingJobs.add("[jobName] : " + jobName + " [groupName] : "
                            + jobGroup + " - " + nextFireTime);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return currentlyExecutingJobs;
    }

    public boolean delete(String jobKey) throws SchedulerException {
        return scheduler.deleteJob(JobKey.jobKey(jobKey));
    }

    private JobDetail buildJobDetail(Object action){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("action", action);
        return JobBuilder.newJob(SchedulerExecutor.class).usingJobData(jobDataMap).build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withMisfireHandlingInstructionFireNow())
                .build();
    }

    private Response saveAction(ActionSchedule action, ZonedDateTime dateTime) {
        DocSchedule doc = docScheduleService.findById(dateTime.toLocalDate().toString());
        Set<ActionSchedule> schedule = doc.getActionSchedule();
        schedule.add(action);
        return docScheduleService.update(doc);
    }

    public List<ActionSchedule> find() throws IOException {
        return scheduleRepository(false).getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(ActionSchedule.class);
    }

    public DbInfo reinitializeDb(){
        //docScheduleService.save(new DocSchedule(ZonedDateTime.now().toLocalDate().toString()));
        //client.deleteDB("schedule");
        //client.createDB("schedule");
        return client.database("schedule", false).info();
    }

    private Database scheduleRepository(boolean create){
        return client.database("schedule", create);
    }
}
