package com.example.websocket.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cloudant")
public class CloudantSettings {

    private String username;
    private String password;
    private String url;
    private int timeout = 60000;
    private boolean autoInitialize = true;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isAutoInitialize() {
        return autoInitialize;
    }

    public void setAutoInitialize(boolean autoInitialize) {
        this.autoInitialize = autoInitialize;
    }
}
