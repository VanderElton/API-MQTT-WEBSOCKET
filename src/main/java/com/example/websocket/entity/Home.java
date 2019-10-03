package com.example.websocket.entity;

import com.cloudant.client.api.model.Document;
import com.example.websocket.entity.device.Device;
import com.example.websocket.entity.scene.Scene;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

public class Home extends Document {

    private String name;

    private String description;

    @Valid
    private Set<Device> devices;

    @Valid
    private Set<Scene> scenes;

    private Set<HomePermission> permissions;

    public Home() {
        super();
        this.devices = new HashSet<>();
        this.scenes = new HashSet<>();
        this.permissions = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public Set<HomePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<HomePermission> permissions) {
        this.permissions = permissions;
    }

    public Set<Scene> getScenes() {
        return scenes;
    }

    public void setScenes(Set<Scene> scenes) {
        this.scenes = scenes;
    }
}
