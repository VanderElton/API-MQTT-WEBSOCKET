package com.example.websocket.service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.example.websocket.entity.schedule.DocSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocScheduleService {

    @Autowired
    private CloudantClient client;

    public Response delete(String homeId){
        return docScheduleRepository(false).remove(findById(homeId));
    }

    public Response save(DocSchedule doc){
        return docScheduleRepository(true).save(doc);
    }

    public Response update(DocSchedule doc){
        return docScheduleRepository(true).update(doc);
    }

    public List<DocSchedule> findAll(){
        return docSchedule();
    }

    public DocSchedule findById(String date){
        return docScheduleRepository(true).find(DocSchedule.class, date);
    }

    private List<DocSchedule> docSchedule(){
        List<DocSchedule> docs = new ArrayList<>();
        try {
            docs = docScheduleRepository(false).getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(DocSchedule.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return docs;
    }

    private Database docScheduleRepository(boolean create){
        return client.database("docschedule", create);
    }
}
