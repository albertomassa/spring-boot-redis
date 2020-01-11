package com.am.springredis.repository;

import com.am.springredis.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.redis.port=6379"})
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    public void shouldSaveUser_toRedis() {
        Person person = new Person("name", "surname");
        person = repository.save(person);
        assertNotNull(person.getUuid());

        String uuid = person.getUuid();

        Person saved = repository.findById(uuid).get();
        assertNotNull(saved);
        assertEquals(true, person.equals(saved));
    }

}
