package com.example.Logingestor.repository;

import com.example.Logingestor.model.LogModel;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LogEntryRepository extends JpaRepository<LogModel, Long> {
}
