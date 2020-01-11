package com.am.springredis.publishsubscribe;

import com.am.springredis.model.RedisMessage;
import com.am.springredis.publisher.Publisher;
import com.am.springredis.subscriber.Subscriber;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.redis.port=6379","spring.application.profiles.active=test"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPublishSubscribe {

    @Autowired
    private Publisher publisher;

    @Autowired
    private Subscriber subscriber;

    @Test
    public void testPublish() {
        String message = "a simple test message";
        publisher.publish(message);
    }

    @Test
    public void testSubscribe() {
        List<RedisMessage> receivedMessages = subscriber.getMessages();
        assertNotNull(receivedMessages);
        assertEquals(1, receivedMessages.size());
        assertEquals("a simple test message", receivedMessages.get(0).getValue());
    }

}
