package com.example.websocket.service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.example.websocket.entity.Home;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {

    protected static final Log LOGGER = LogFactory.getLog(HomeService.class);

    private String HOME = "home";

    @Autowired
    private CloudantClient client;

    public Response delete(String homeId){
        return homeRepository(false).remove(findById(homeId));
    }

    public Response save(Home home){
        return homeRepository(true).save(home);
    }

    public Response update(Home home){
        return homeRepository(false).update(home);
    }

    public List<Home> findAll(){
        return homes();
    }

    public Home findById(String homeId){
        return homeRepository(false).find(Home.class, homeId);
    }

    private List<Home> homes(){
        List<Home> homes = new ArrayList<>();
        try {
            homes = homeRepository(false).getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(Home.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return homes;
    }

    Database homeRepository(boolean create){
        return client.database(HOME, create);
    }
}
