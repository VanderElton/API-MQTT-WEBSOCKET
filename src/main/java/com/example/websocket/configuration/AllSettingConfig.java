package com.example.websocket.configuration;

import com.example.websocket.mqtt.IotHttpDeviceManager;
import com.example.websocket.mqtt.IotMqttDeviceManager;
import com.example.websocket.settings.CloudantSettings;
import com.example.websocket.settings.IotSettings;
import com.example.websocket.settings.QuartzSettings;
import com.ibm.iotf.client.app.ApplicationClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = { "com.example.websocket.settings" })
@EnableConfigurationProperties
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AllSettingConfig {

    private final Log logger = LogFactory.getLog(getClass());

    @Bean
    public IotSettings iotSettings(){ return new IotSettings(); };

    @Bean
    public QuartzSettings quartzSettings() {
        return new QuartzSettings();
    }

    @Bean
    public CloudantSettings cloudantSettings() { return new CloudantSettings(); }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ApplicationClient applicationClient() {
        Properties props = new Properties();
        props.put("org", iotSettings().getOrg());
        props.put("API-Key", iotSettings().getApiKey());
        props.put("Authentication-Token", iotSettings().getApiToken());
        props.put("id", "app" + (Math.random() * 10000));
        props.put("Authentication-Method", "apikey");
        props.put("Automatic-Reconnect", iotSettings().isAutoRetry());

        ApplicationClient applicationClient = null;

        try {
            applicationClient = new ApplicationClient(props);
        } catch (Exception e) {
            logger.error("Creating client.", e);
        }
        return applicationClient;
    }

    @Bean
    public IotMqttDeviceManager iotMqttDeviceManager() {
        return new IotMqttDeviceManager();
    }

    @Bean
    public IotHttpDeviceManager iotHttpDeviceManager() {
        return new IotHttpDeviceManager();
    }

}
