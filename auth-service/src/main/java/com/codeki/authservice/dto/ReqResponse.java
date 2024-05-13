package com.codeki.authservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqResponse {

    private String username;
    private String password;
    private String email;
    private String role;

    private String name;
    private String lastName;
    private String passport;
    private String dni;

    private String message;
    private int statusCode;
    private String token;
    private String expirationTime;
    private String error;

}
