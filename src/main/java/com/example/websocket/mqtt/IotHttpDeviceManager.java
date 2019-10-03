package com.example.websocket.mqtt;

import com.example.websocket.configuration.SpringContext;
import com.example.websocket.entity.device.Device;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.iotf.client.IoTFCReSTException;
import com.ibm.iotf.client.api.APIClient;
import com.ibm.iotf.client.app.ApplicationClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDateTime;

public class IotHttpDeviceManager {

    private JsonParser parser = new JsonParser();

    private static final Log LOGGER = LogFactory.getLog(IotHttpDeviceManager.class);

    public void createDevice(String homeId, Device device) {
        JsonElement deviceInfo = parser.parse("{\"serialNumber\": " + "\"" + device.getId() + "\", \"fwVersion\":\"" + device.getFwVersion() + "\", \"hwVersion\":\"" + device.getHwVersion()
                + "\", \"description\":" + "\"" + homeId + "\" }");
        String locationToBeAdded = "{\"longitude\": 0, \"latitude\": 0, \"elevation\": 0,\"measuredDateTime\": \"" + LocalDateTime.now() + "\"}";
        JsonElement location = parser.parse(locationToBeAdded);
        try {
            JsonObject result = apiClient().registerDevice(device.getProductModelId(), device.getId(), device.getAuthToken(), deviceInfo, location, null).getAsJsonObject();
            if (result.has("authToken")) {
                device.setAuthToken(result.get("authToken").getAsString());
            }
        } catch (IoTFCReSTException e) {
            LOGGER.error("Error createDevice " + device.getId() + " exception: " + e.getMessage());
        }
    }

    private APIClient apiClient(){
        return SpringContext.getBean(ApplicationClient.class).api();
    }
}
