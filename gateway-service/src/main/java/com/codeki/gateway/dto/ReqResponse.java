package com.codeki.gateway.dto;

import lombok.Data;

@Data
public class ReqResponse {

    private String username;
    private String password;
    private String email;
    private String role;
    private String message;
    private int statusCode;
    private String token;
    private String expirationTime;
    private String error;

}
