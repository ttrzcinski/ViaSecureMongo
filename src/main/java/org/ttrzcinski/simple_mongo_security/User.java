package org.ttrzcinski.simple_mongo_security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@NoArgsConstructor
@ToString
@Setter
@Getter
public class User {

    @Id
    public String id;

    private String username;
    private String roleName;

    public User(String username, String roleName) {
        this.username = username;
        this.roleName = roleName;
    }

}
