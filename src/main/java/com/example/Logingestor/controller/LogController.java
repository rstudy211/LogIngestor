package com.example.Logingestor.controller;

import com.example.Logingestor.dto.LogSearchRequest;
import com.example.Logingestor.dto.LogSearchResponse;
import com.example.Logingestor.model.LogModel;
import com.example.Logingestor.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping("/ingest")
    public ResponseEntity<String> ingestLog(@RequestBody LogModel logModel) {

        System.out.println(logModel.toString());
        logService.ingestLog(logModel);
        return ResponseEntity.ok("Log ingested successfully"+logModel.getId());
    }

    @GetMapping("/search")
    public LogSearchResponse searchLogs(@RequestBody LogSearchRequest logSearchRequest) throws IOException {
        System.out.println("we are in search");

        LogSearchResponse searchRes=new LogSearchResponse();
        searchRes.setQuery(logSearchRequest.getQuery());
        searchRes.setFilters(logSearchRequest.getFilters());
        List<LogModel> logs = logService.matchLogWithFilters(logSearchRequest.getQuery(),logSearchRequest.getFilters());
        searchRes.setLogModelList(logs);
        searchRes.setCount(logs.stream().count());


        return searchRes;
    }



//    @GetMapping("/all")
//    public Iterable<LogDocument> findAll() {
//        return logService.getAllLogs();
//    }
}
