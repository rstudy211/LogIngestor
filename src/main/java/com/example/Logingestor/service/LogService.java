package com.example.Logingestor.service;

import com.example.Logingestor.model.LogModel;
import com.example.Logingestor.repository.LogEntryRepository;
import com.example.Logingestor.util.LogEntrySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogService {

    @Autowired
    private  final LogEntryRepository logEntryRepository;

    public void ingestLog(LogModel logModel) {
        logEntryRepository.save(logModel);
    }

    public List<LogModel> matchLogWithFilters(String query, Map<String, String> filters) {
        if ( query.isEmpty()  && filters.isEmpty()){
            return logEntryRepository.findAll();
        }
        if (filters.isEmpty()){
            return logEntryRepository.findAllByQuery(query);
        }
//        List<LogModel> logModels = new ArrayList<>();
//        for (Map.Entry<String, String> entry : filters.entrySet()) {
//            switch (entry.getKey()) {
//                case "level":
//                    logModels.addAll(logEntryRepository.findByLevel(entry.getValue()));
//                    break;
//                case "message":
//                    logModels.addAll(logEntryRepository.findByMessage(entry.getValue()));
//                    break;
//                case "resourceId":
//                    logModels.addAll(logEntryRepository.findByResourceId(entry.getValue()));
//                    break;
//                case "spanId":
//                    logModels.addAll(logEntryRepository.findBySpanId(entry.getValue()));
//                    break;
//                case "commit":
//                    logModels.addAll(logEntryRepository.findByCommit(entry.getValue()));
//                    break;
//                case "traceId":
//                    logModels.addAll(logEntryRepository.findByTraceId(entry.getValue()));
//                    break;
//                // add more cases as needed
//                default:
//                    // handle default case if needed
//                    break;
//            }
//        }


//        return  logEntryRepository.findAllByQueryAndFilters(query,filters);

        return findByFilters(filters);
    }

    public List<LogModel> findByFilters(Map<String, String> filters) {
        LogModel exampleEntity = new LogModel();
        exampleEntity.setLevel(filters.getOrDefault("level", null));
        exampleEntity.setMessage(filters.getOrDefault("message", null));
        exampleEntity.setResourceId(filters.getOrDefault("resourceId", null));
        exampleEntity.setSpanId(filters.getOrDefault("spanId", null));

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")  // Ignore the 'id' field during matching
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Use CONTAINING for partial matching

        Example<LogModel> example = Example.of(exampleEntity,exampleMatcher);


        return logEntryRepository.findAll(example);
    }
}
