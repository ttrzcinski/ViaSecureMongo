package org.ttrzcinski.simple_mongo_security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Example;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class UserRepositoryTest {

    @Container
    @ServiceConnection
    static MongoDBContainer container = new MongoDBContainer("mongo:7.0.2");

    @Autowired
    UserRepository repository;

    User testAdmin, testHelper, testGuest;

    @BeforeEach
    public void setUp() {

        repository.deleteAll();

        testAdmin = repository.save(new User("TestAdmin", "TheAdmin"));
        testHelper = repository.save(new User("TestHelper", "TheHelper"));
        testGuest = repository.save(new User("TestGuest", "TheGuest"));
    }

    @Test
    public void setsIdOnSave() {

        User dave = repository.save(new User("TestAdmin", "TheAdmin"));

        assertThat(dave.id).isNotNull();
    }

    @Test
    public void findsByRoleName() {

        List<User> result = repository.findByRoleName("TheAdmin");

        assertThat(result).hasSize(1).extracting("username").contains("TestAdmin");
    }

    @Test
    public void findsByExample() {

        User probe = new User(null, "TheGuest");

        List<User> result = repository.findAll(Example.of(probe));

        assertThat(result).hasSize(2).extracting("username").contains("TestGuest");
    }
}