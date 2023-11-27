package com.example.Logingestor.util;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ElasticSearchUtil {

    public static Supplier<Query> supplier(){
        Supplier<Query> supplier = ()->Query.of(q->q.matchAll(matchAllQuery()));
        return supplier;
    }

    public static MatchAllQuery matchAllQuery(){
        val  matchAllQuery = new MatchAllQuery.Builder();
        return matchAllQuery.build();
    }

    public static Supplier<Query> supplierWithLevelField(String fieldValue){
        Supplier<Query> supplier = ()->Query.of(q->q.match(matchQueryWithLevelField(fieldValue)));
        return supplier;
    }



    public static MatchQuery matchQueryWithLevelField(String fieldValue){
        val  matchQuery = new MatchQuery.Builder();
        return matchQuery.field("level").query(fieldValue).build();
    }

    public static Supplier<Query> supplierQueryForBoolQuery(Map<String,String> filters){
        Supplier<Query> supplier = ()->Query.of(q->q.bool(boolQuery(filters)));
        return supplier;
    }

    public static BoolQuery boolQuery(Map<String,String> filters){
        val boolQuery  = new BoolQuery.Builder();

        return boolQuery.filter(matchQuery(filters)).build();
    }

    public static List<Query> termQuery(Map<String,String> filters){
        final List<Query> terms = new ArrayList<>();
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            val termQuery = new TermQuery.Builder();
            Query termQueryObject = Query.of(q -> q.term(termQuery.field(entry.getKey()).value(entry.getValue()).build()));
            terms.add(termQueryObject);
        }
        return terms;
    }

    public static List<Query> matchQuery(Map<String,String> filters){
        final List<Query> matches = new ArrayList<>();
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            val matchQuery = new MatchQuery.Builder();
            Query matchQueryObject = Query.of(q -> q.match(matchQuery.field(entry.getKey()).query(entry.getValue()).build()));
            matches.add(matchQueryObject);
        }
        return matches;
    }
}