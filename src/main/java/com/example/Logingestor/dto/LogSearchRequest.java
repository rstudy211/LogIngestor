package com.example.Logingestor.dto;

import lombok.*;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogSearchRequest {
    private String query;
    private Map<String, String> filters;


}
