package com.example.websocket.resource;

import com.cloudant.client.api.model.Response;
import com.example.websocket.entity.CommandMqtt;
import com.example.websocket.entity.MsgCallback;
import com.example.websocket.entity.device.Device;
import com.example.websocket.repository.CommandRepository;
import com.example.websocket.repository.MessageRepository;
import com.example.websocket.service.DeviceService;
import com.example.websocket.service.MessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;


@RestController
@Api("Device")
@RequestMapping("/api/device")
public class DeviceResource {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/{homeId}")
    public ResponseEntity<Response> save(@PathVariable String homeId, @Valid @RequestBody Device device){
        return ResponseEntity.ok(deviceService.save(homeId, device));
    }

    @GetMapping("/home/{homeId}")
    public ResponseEntity<Set<Device>> findAll(@PathVariable String homeId){
        return ResponseEntity.ok(deviceService.findAll(homeId));
    }

    @GetMapping("/home/{homeId}/device/{deviceId}")
    public ResponseEntity<Device> findById(@PathVariable String homeId, @PathVariable String deviceId) {
        return ResponseEntity.ok(deviceService.findById(homeId, deviceId));
    }

    @DeleteMapping("/home/{homeId}/device/{deviceId}")
    public ResponseEntity<Response> delete(@PathVariable String homeId, @PathVariable String deviceId) {
        return ResponseEntity.ok(deviceService.delete(homeId, deviceId));
    }
}
