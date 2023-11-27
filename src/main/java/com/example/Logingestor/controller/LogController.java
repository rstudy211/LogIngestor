package com.example.Logingestor.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.Logingestor.dto.LogSearchRequest;
import com.example.Logingestor.model.LogDocument;
import com.example.Logingestor.model.LogModel;
import com.example.Logingestor.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        logService.ingestLog(logModel);
        return ResponseEntity.ok("Log ingested successfully");
    }

    //    @GetMapping("/search")
//    public List<LogDocument> searchLogs(@RequestParam String query, @RequestParam Map<String, String> filters) {
//        System.out.println("we are in search"+ query + filters);
//        List<LogDocument> searchResults = logService.searchLogs(query, filters);
//        return searchResults;
//    }
    @GetMapping("/search")
    public List<LogDocument> searchLogs(@RequestBody LogSearchRequest logSearchRequest) throws IOException {
        System.out.println(logSearchRequest.toString());
//        SearchResponse<LogDocument> searchResponse = logService.matchLogWithLevelField(logSearchRequest.getQuery());
//    System.out.println(searchResponse.toString());

//        List<Hit<LogDocument>> listOfHits = searchResponse.hits().hits();
//        List<LogDocument> listOfLogDocuments = new ArrayList<>();
//        for (Hit<LogDocument> hit : listOfHits) {
//            listOfLogDocuments.add(hit.source());
//        }
//        return listOfLogDocuments;
        return logService.matchLogWithFilters(logSearchRequest.getQuery(),logSearchRequest.getFilters());
    }



    @GetMapping("/all")
    public Iterable<LogDocument> findAll() {
        return logService.getAllLogs();
    }
}
