package com.example.websocket.resource;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.model.Response;
import com.example.websocket.entity.CommandMqtt;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api("Databases")
@RequestMapping({"/api/databases"})
public class CloudantController {

    @Autowired
    private CloudantClient client;

    @GetMapping("/databases")
    public @ResponseBody List<String> data() {
        return client.getAllDbs();
    }
}
