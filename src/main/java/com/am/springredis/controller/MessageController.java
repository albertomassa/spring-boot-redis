package com.am.springredis.controller;

import com.am.springredis.model.RedisMessage;
import com.am.springredis.publisher.Publisher;
import com.am.springredis.subscriber.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private Publisher publisher;

    @Autowired
    private Subscriber subscriber;

    @PostMapping(value = "/messages")
    public ResponseEntity send(@RequestBody String message) {
        publisher.publish(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/messages")
    public ResponseEntity<List<RedisMessage>> retrieve() {
        return new ResponseEntity<>(subscriber.getMessages(), HttpStatus.OK);
    }

}
