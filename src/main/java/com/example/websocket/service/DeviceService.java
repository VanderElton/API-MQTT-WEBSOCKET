package com.example.websocket.service;

import com.cloudant.client.api.model.Response;
import com.example.websocket.entity.Home;
import com.example.websocket.entity.device.Device;
import com.example.websocket.entity.device.TokenBuilder;
import com.example.websocket.exception.ApiException;
import com.example.websocket.mqtt.IotHttpDeviceManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class DeviceService {

    protected static final Log LOGGER = LogFactory.getLog(DeviceService.class);

    @Autowired
    private HomeService homeService;

    @Autowired
    private IotHttpDeviceManager iotHttpDeviceManager;

    public Response save(String homeId, Device device){
        Home home = homeService.findById(homeId);
        Set<Device> devices = home.getDevices();
        if(verifySingleDevice(devices, device.getId())) {
            device.setAuthToken(initializeAuthToken(device));
            iotHttpDeviceManager.createDevice(homeId, device);
            devices.add(device);
        }else{
            LOGGER.warn(device.getId() + " already exist.");
            String[] params = { device.getId() };
            throw new ApiException("http.conflict", HttpStatus.CONFLICT, params);
        }
        return homeService.update(home);
    }

    public Response delete(String homeId, String deviceId){
        Home home = homeService.findById(homeId);
        Device device = findInHome(home, deviceId);
        home.getDevices().remove(device);
        return homeService.update(home);
    }

    public Device findById(String homeId, String deviceId){
        return findInHome(homeService.findById(homeId), deviceId);
    }

    public Set<Device> findAll(String homeId){
        return homeService.findById(homeId).getDevices();
    }

    private Device findInHome(Home home, String deviceId){
        Optional<Device> device = home.getDevices()
                .stream()
                .filter(e -> deviceId.equals(e.getId()))
                .findFirst();
        return device.orElse(null);
    }

    private boolean verifySingleDevice(Set<Device> devices, String deviceId){
        return devices.stream().noneMatch(device -> device.getId().equals(deviceId));
    }

    public String initializeAuthToken(Device device) {
        return new TokenBuilder().createToken(device);
    }
}
