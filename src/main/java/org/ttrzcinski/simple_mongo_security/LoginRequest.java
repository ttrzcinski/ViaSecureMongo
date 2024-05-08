package org.ttrzcinski.simple_mongo_security;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginRequest {
    private String username;
    private String password;
}