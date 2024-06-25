package com.ttknpdev.backend.entities.sign_in_and_jwt;

import lombok.Data;

/*
    This class is required for storing the username and password we recieve from the client.
*/
@Data
public class SignIn {
    private String username;
    private String password;
}
