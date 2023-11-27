package com.example.Logingestor.repository;

import com.example.Logingestor.model.LogDocument;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LogDocumentRepository extends ElasticsearchRepository<LogDocument, Long> {

    @Query("{\"bool\": {\"must\": ?0}}")
    List<LogDocument> findByCustomQuery(QueryBuilder queryBuilder);
}
