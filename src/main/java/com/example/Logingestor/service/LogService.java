package com.example.Logingestor.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.util.ObjectBuilder;
import com.example.Logingestor.model.LogDocument;
import com.example.Logingestor.model.LogModel;
import com.example.Logingestor.repository.LogDocumentRepository;
import com.example.Logingestor.repository.LogEntryRepository;
import com.example.Logingestor.util.ElasticSearchUtil;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.MoreLikeThisQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.regexp;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;

@Service
@RequiredArgsConstructor
public class LogService {

    @Autowired
    private final LogDocumentRepository logDocumentRepository;

//    @Autowired
//    private final LogEntryRepository logEntryRepository;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private ElasticsearchTemplate elasticsearchTemplate;
//    @Autowired
//    public  LogService(LogEntryRepository logEntryRepository, LogDocumentRepository logDocumentRepository) {
//        this.logEntryRepository = logEntryRepository;
//        this.logDocumentRepository = logDocumentRepository;
//    }

    public void ingestLog(LogModel logEntry) {
        // Save to PostgreSQL
//        logEntryRepository.save(logEntry);

        // Save to Elasticsearch
        LogDocument logDocument = convertToLogDocument(logEntry);
        logDocumentRepository.save(logDocument);
    }

    private LogDocument convertToLogDocument(LogModel logModel) {
        // Convert LogEntry to LogDocument
        // Implement the conversion logic based on your requirements
        LogModel.MetaData metaData = logModel.getMetaData();
        String parentResourceId = metaData != null ? metaData.getParentResourceId() : null;

        // Convert timestamp from string to Instant

        return LogDocument.builder()
                .level(logModel.getLevel())
                .message(logModel.getMessage())
                .resourceId(logModel.getResourceId())
                .timestamp(logModel.getTimestamp())
                .traceId(logModel.getTraceId())
                .spanId(logModel.getSpanId())
                .commit(logModel.getCommit())
                .parentResourceId(parentResourceId)
                .build();
    }

//    public List<LogDocument> searchLogs(String query, Map<String, String> filters) {
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//
////        Add full text search query
//        if (query != null && !query.isEmpty()){
//            boolQueryBuilder.must(QueryBuilders.queryStringQuery(query));
//        }
////        Add filters
//        for(Map.Entry<String, String> entry: filters.entrySet()){
//            String field  = entry.getKey();
//            String value = entry.getValue();
//
//            if (value!= null && !value.isEmpty()){
//                if("timestamp".equals(field)){
//                    RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field)
//                            .from(Instant.parse(value).toString())
//                            .to(Instant.parse(value).plusSeconds(1).toString());
//                    boolQueryBuilder.must(rangeQueryBuilder);
//                } else {
//                    // Add term query for other fields
//                    boolQueryBuilder.must(new TermQueryBuilder(field, value));
//
//                }
//            }
//        }
//
////        QueryBuilder qb1 = QueryBuilders.matchQuery("level", "error");
////
////        System.out.println(qb1.toString());
////
//
//
//        boolQueryBuilder.must(QueryBuilders.matchQuery("level", "error"));
//
//        // Build NativeSearchQuery
////        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();
////        SearchHits<LogDocument> searchHits = elasticsearchTemplate.search(searchQuery, LogDocument.class);
////
//
//        return searchHits.stream().map(SearchHit::getContent).toList();
//    }

    public SearchResponse<LogDocument> matchLogWithLevelField(String fieldValue) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplierWithLevelField(fieldValue);
        System.out.println("elastic search query is"+ supplier.get().toString());

        SearchResponse<LogDocument> searchResponse = elasticsearchClient.search(s->s.index("logs-index").query(supplier.get()),LogDocument.class);
        return searchResponse;
    }

    public List<LogDocument> matchLogWithFilters(String query, Map<String,String> filters) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplierQueryForBoolQuery(filters);
        System.out.println("elastic search query is"+ supplier.get().toString());

        SearchResponse<LogDocument> searchResponse = elasticsearchClient.search(s->s.index("logs-index").query(supplier.get()),LogDocument.class);
        List<Hit<LogDocument>> listOfHits = searchResponse.hits().hits();
        List<LogDocument> listOfLogDocuments = new ArrayList<>();
        for (Hit<LogDocument> hit : listOfHits) {
            listOfLogDocuments.add(hit.source());
        }
        return listOfLogDocuments;
    }
    public Iterable<LogDocument> getAllLogs() {
        return logDocumentRepository.findAll();
    }
}
