package com.example.websocket.resource;

import com.example.websocket.entity.CommandMqtt;
import com.example.websocket.entity.MsgCallback;
import com.example.websocket.repository.CommandRepository;
import com.example.websocket.repository.MessageRepository;
import com.example.websocket.service.MessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Api("Message")
@RequestMapping({ "api/messages" })
public class MessageResource {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private MessageService messageService;

    @GetMapping("/receivedEvents")
    public List<MsgCallback> findAllEvents() throws IOException {
        return messageService.findAllEvents();
    }

    @GetMapping("/sendCommands")
    public List<CommandMqtt> findAllCommands() throws IOException {
        return messageService.findAllCommands();
    }

    @GetMapping("/evt")
    public List<MsgCallback> events() {
        return messageRepository.findAll();
    }

    @GetMapping("/cmd")
    public List<CommandMqtt> commands() {
        return commandRepository.findAll();
    }
}
