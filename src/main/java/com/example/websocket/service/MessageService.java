package com.example.websocket.service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.example.websocket.entity.CommandMqtt;
import com.example.websocket.entity.MsgCallback;
import com.example.websocket.repository.MessageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MessageService {

    protected static final Log LOGGER = LogFactory.getLog(MessageService.class);

    @Autowired
    private CloudantClient client;

    public Response saveEvent(MsgCallback msgCallback){
        return eventsRepository().save(msgCallback);
    }

    public Response saveCommand(CommandMqtt commandMqtt){
        return commandsRepository().save(commandMqtt);
    }

    public List<MsgCallback> findAllEvents() throws IOException {
         return eventsRepository().getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(MsgCallback.class);
    }

    public List<CommandMqtt> findAllCommands() throws IOException {
        return commandsRepository().getAllDocsRequestBuilder().build().getResponse().getDocsAs(CommandMqtt.class);
    }

    private Database eventsRepository(){
        return client.database("events", true);
    }
    private Database commandsRepository(){
        return client.database("commands", true);
    }
}
