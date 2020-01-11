package com.am.springredis.configuration;

import com.am.springredis.model.Person;
import com.am.springredis.publisher.Publisher;
import com.am.springredis.repository.PersonRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log

@Component
public class InitConfiguration {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private Publisher publisher;

    @Value("${spring.application.profiles.active}")
    private String profile;

    @EventListener(ApplicationStartedEvent.class)
    public void person() {
        if(profile.equals("test")) return;
        log.info("***person_init");
        Person person = new Person("person:name", "person:surname");
        repository.save(person);
        log.info(person.toString());
    }

    @EventListener(ApplicationStartedEvent.class)
    public void message() {
        if(profile.equals("test")) return;
        log.info("***message_init");
        publisher.publish("a simple message");
    }

}
