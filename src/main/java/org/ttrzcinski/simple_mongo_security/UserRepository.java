package org.ttrzcinski.simple_mongo_security;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "Users", path = "Users")
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(@Param("name") String username);
    List<User> findByRoleName(@Param("name")String roleName);

}