package com.codeki.tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyDto {

    // Dto para el FeignClient
    private Long id;
    private String name;
    private String page;
    private String banner;

}
