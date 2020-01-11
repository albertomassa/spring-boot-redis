package com.am.springredis.configuration;

import com.am.springredis.model.RedisMessage;
import com.am.springredis.subscriber.Subscriber;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import javax.annotation.PreDestroy;

@Log

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;

    @Autowired
    RedisConnectionFactory factory;

    @Autowired
    Subscriber subscriber;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        log.info("***redisConnectionFactory");
        log.info("host: " + host);
        log.info("port: " + port);
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @PreDestroy
    public void cleanRedis() {
        factory.getConnection()
                .flushDb();
    }

    /*
    PUBLISH/SUBSCRIBE CONFIGURATION
     */

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("redis_topic");
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(subscriber, topic());
        return container;
    }

    @Bean
    public RedisTemplate<String, RedisMessage> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, RedisMessage> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisMessage.class));
        return template;
    }

}
