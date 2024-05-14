package com.codeki.authservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResp {

    private int statusCode;
    private String message;

}
