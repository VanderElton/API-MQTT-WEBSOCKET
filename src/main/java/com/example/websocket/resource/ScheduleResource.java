package com.example.websocket.resource;

import com.example.websocket.entity.CommandMqtt;
import com.example.websocket.entity.schedule.ActionSchedule;
import com.example.websocket.entity.schedule.ScheduleResponse;
import com.example.websocket.service.ScheduleService;
import io.swagger.annotations.Api;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Api("Schedule")
@RequestMapping("/api/schedule")
public class ScheduleResource {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/scheduleCommand")
    public ResponseEntity<ScheduleResponse> scheduleCommand(@Valid @RequestBody ActionSchedule actionSchedule){
        return ResponseEntity.ok(scheduleService.create(actionSchedule));
    }

    @PostMapping("home/{homeId}/scene/{sceneId}/{dateTime}/{zoneId}/schedule")
    public ResponseEntity<Map<String, Boolean>> scheduleScene(@PathVariable String homeId, @PathVariable String sceneId, @PathVariable String dateTime, @PathVariable String zoneId){
        return ResponseEntity.ok(scheduleService.scheduleScene(homeId, sceneId, dateTime, zoneId));
    }

    @GetMapping("/scheduledCommands")
    public ResponseEntity<List<String>> scheduledCommands(){
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @GetMapping("/cloudant")
    public ResponseEntity<List<ActionSchedule>> scheduled() throws IOException {
        return ResponseEntity.ok(scheduleService.find());
    }

    @DeleteMapping("/scheduledCommands/{jobKey}")
    public ResponseEntity<Boolean> deleteScheduledCommands(@PathVariable String jobKey) throws SchedulerException {
        return ResponseEntity.ok(scheduleService.delete(jobKey));
    }
}
