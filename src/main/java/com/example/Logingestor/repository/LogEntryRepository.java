package com.example.Logingestor.repository;

import com.example.Logingestor.model.LogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;


public interface LogEntryRepository extends JpaRepository<LogModel, Long> {
    @Query("SELECT l FROM LogModel l WHERE CONCAT(l.level, ' ', l.message, ' ', l.resourceId, ' ', l.timestamp, ' ', l.traceId, ' ', l.spanId, ' ', l.commit, ' ', l.metaData.parentResourceId) LIKE %:query%")
    List<LogModel> findAllByQuery(String query);

    @Query("SELECT l FROM LogModel l WHERE l.level=:value")
    List< LogModel> findByLevel(String value);
 @Query("SELECT l FROM LogModel l WHERE l.level=:value")
    List< LogModel> findByMessage(String value);
 @Query("SELECT l FROM LogModel l WHERE l.resourceId=:value")
    List< LogModel> findByResourceId(String value);
 @Query("SELECT l FROM LogModel l WHERE l.traceId=:value")
    List< LogModel> findByTraceId(String value);
 @Query("SELECT l FROM LogModel l WHERE l.spanId=:value")
    List< LogModel> findBySpanId(String value);
 @Query("SELECT l FROM LogModel l WHERE l.commit=:value")
    List< LogModel> findByCommit(String value);


//    @Query("SELECT le FROM LogModel le " +
//            "WHERE CONCAT(le.level, ' ', le.message, ' ', le.resourceId) LIKE %:query% " +
//            "AND le.spanId = :spanId " +
//            "AND le.level = :level")
//    List<LogModel> findAllByQueryAndFilters(String query, Map<String, String> filters);
}
