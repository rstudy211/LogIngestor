package com.example.Logingestor.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
//@Table(name="log_entries")
//@Table()
@Table( name= "log_entries", indexes = {
        @Index(name = "idx_level", columnList = "level"),
        @Index(name = "idx_resourceId", columnList = "resourceId"),
//        @Index(name = "idx_message", columnList = "message"),
        @Index(name = "idx_timestamp", columnList = "log_timestamp"),
        @Index(columnList = "level, message, resourceId", name = "idx_search")

})
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
    @Column(name = "log_timestamp")
    private Instant timestamp;
    private String traceId;
    private String spanId;
    private String commit;

    private MetaData metaData;

    @Getter
    @Setter
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor

    public static class MetaData {
        private String parentResourceId;
    }
}
