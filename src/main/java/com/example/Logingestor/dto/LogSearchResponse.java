package com.example.Logingestor.dto;

import com.example.Logingestor.model.LogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class LogSearchResponse {
    private Long count;
    private String query;
    private Map<String,String> filters;
    private List<LogModel> logModelList;
}
