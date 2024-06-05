package com.amazon.repository;

import com.amazon.model.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
    ErrorLog findByUrl(String url);
}
