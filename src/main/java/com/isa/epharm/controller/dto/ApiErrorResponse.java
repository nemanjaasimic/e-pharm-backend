package com.isa.epharm.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
public class ApiErrorResponse {

    private int statusCode;

    private String message;

    private ZonedDateTime dateTime;

    private List<String> data;

}
