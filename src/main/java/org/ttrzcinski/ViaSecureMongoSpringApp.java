package org.ttrzcinski;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.ttrzcinski.simple_mongo_security.User;
import org.ttrzcinski.simple_mongo_security.UserRepository;

@Slf4j
@OpenAPIDefinition(info = @Info(
        title = "Secure REST API for Mongo", version = "3.0",
        description = "Methods needed to operate with Spring Security and MongoDB."))
@SpringBootApplication
public class ViaSecureMongoSpringApp implements CommandLineRunner {

    @Autowired
    private UserRepository repository;
    
    private static boolean LOAD_THAT_START_DATA = false;

    public static void main(String[] args) {
        SpringApplication.run(ViaSecureMongoSpringApp.class, args);
    }

    @Override
    public void run(String... args) {
        
        if (!LOAD_THAT_START_DATA) {
            return;
        }

        repository.deleteAll();

        // save a couple of Users
        repository.save(new User("TestUser1", "TestUserRole1"));
        repository.save(new User("TestUserRole2", "TestUserRole2"));

        // fetch all Users
        log.info("Users found with findAll():");
        log.info("-------------------------------");
        for (User User : repository.findAll()) {
            log.info(String.valueOf(User));
        }
        log.info("...");

        // fetch an individual User
        log.info("User found with findByUsername('TestUser1'):");
        log.info("--------------------------------");
        log.info(String.valueOf(repository.findByUsername("TestUser1")));

        log.info("Users found with findByRoleName('TestUserRole2'):");
        log.info("--------------------------------");
        for (User User : repository.findByRoleName("TestUserRole1")) {
            log.info(String.valueOf(User));
        }

    }

}