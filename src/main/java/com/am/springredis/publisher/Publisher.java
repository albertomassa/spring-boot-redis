package com.am.springredis.publisher;

import com.am.springredis.model.RedisMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@AllArgsConstructor

@Log

@Component
public class Publisher {

    @Autowired
    private RedisTemplate<String, RedisMessage> redisTemplate;
    @Autowired
    private ChannelTopic topic;

    public void publish(String value) {
        log.info("***publisher");
        log.info("topic: " + topic.getTopic());
        log.info("message: " + value);
        redisTemplate.convertAndSend(topic.getTopic(), new RedisMessage(value));
    }

}
