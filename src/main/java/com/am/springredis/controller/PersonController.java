package com.am.springredis.controller;

import com.am.springredis.model.Person;
import com.am.springredis.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @GetMapping(value = "/people")
    public ResponseEntity<List<Person>> find() {
        return new ResponseEntity<>((List<Person>) repository.findAll(), HttpStatus.OK);
    }

}
