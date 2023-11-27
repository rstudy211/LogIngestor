package com.example.Logingestor.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Entity
@Data
@Table(name="log_entries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level;
    private String message;
    private String resourceId;
//    @Field(type = FieldType.Date)
    private String timestamp;
    private String traceId;
    private String spanId;
    private String commit;
    private MetaData metaData;

    @Getter
    @Setter
    public static class MetaData{
        private String parentResourceId;
    }
}
