package com.example.Logingestor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "logs-index")
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String level;

    @Field(type = FieldType.Text)
    private String message;

    @Field(type = FieldType.Text)
    private String resourceId;

    @Field(type = FieldType.Text)
    private String timestamp;

    @Field(type = FieldType.Text)
    private String traceId;

    @Field(type = FieldType.Text)
    private String spanId;

    @Field(type = FieldType.Text)
    private String commit;

    @Field(type = FieldType.Text)
    private String parentResourceId;
    // Getter and Setter methods
}
