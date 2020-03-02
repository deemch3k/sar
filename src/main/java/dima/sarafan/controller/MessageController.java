package dima.sarafan.controller;


import com.fasterxml.jackson.annotation.JsonView;
import dima.sarafan.domain.Message;
import dima.sarafan.domain.Views;
import dima.sarafan.dto.EventType;
import dima.sarafan.dto.ObjectType;
import dima.sarafan.repo.MessageRepo;
import dima.sarafan.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageRepo messageRepo;
    private final BiConsumer<EventType, Message> wsSender;

    @Autowired
    public MessageController(MessageRepo messageRepo, WsSender wsSender) {
        this.messageRepo = messageRepo;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        Message updatedMessage = messageRepo.save(message);

        wsSender.accept(EventType.CREATE, updatedMessage);

        return updatedMessage;
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDb, @RequestBody Message message){

        BeanUtils.copyProperties(message, messageFromDb, "id");

        Message updatedMessage = messageRepo.save(messageFromDb);

        wsSender.accept(EventType.UPDATE, updatedMessage);


        return updatedMessage;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepo.delete(message);
        wsSender.accept(EventType.REMOVE, message);


    }

}
