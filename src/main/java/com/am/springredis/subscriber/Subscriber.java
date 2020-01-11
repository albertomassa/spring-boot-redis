package com.am.springredis.subscriber;

import com.am.springredis.model.RedisMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log
@Getter

@Component
public class Subscriber implements MessageListener {

    @Autowired
    private ObjectMapper mapper;

    private List<RedisMessage> messages = new ArrayList<>();

    @Override
    public void onMessage(Message message, byte[] bytes) {
        try {
            RedisMessage redisMessage = mapper.readValue(message.toString(), RedisMessage.class);
            log.info("***subscriber");
            log.info("message: " + redisMessage);
            messages.add(redisMessage);
        }catch (IOException e) {
            e.printStackTrace();
            log.warning("serialization_error");
        }
    }

}
