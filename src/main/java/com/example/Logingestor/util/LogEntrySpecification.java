package com.example.Logingestor.util;

import com.example.Logingestor.model.LogModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class LogEntrySpecification implements Specification<LogModel> {
    @Autowired
    private final Map<String, String> filters;


    @Override
    public Specification<LogModel> and(Specification<LogModel> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<LogModel> or(Specification<LogModel> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<LogModel> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            switch (entry.getKey()) {
                case "level":
                    predicates.add(builder.equal(root.get("level"), entry.getValue()));
                    break;
                case "message":
                    predicates.add(builder.equal(root.get("message"), entry.getValue()));
                    break;
                case "resourceId":
                    predicates.add(builder.equal(root.get("resourceId"), entry.getValue()));
                    break;
                case "spanId":
                    predicates.add(builder.equal(root.get("spanId"), entry.getValue()));
                    break;
                case "commit":
                    predicates.add(builder.equal(root.get("commit"), entry.getValue()));
                    break;
                case "traceId":
                    predicates.add(builder.equal(root.get("traceId"), entry.getValue()));
                    break;
                // Add more cases as needed
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

}
