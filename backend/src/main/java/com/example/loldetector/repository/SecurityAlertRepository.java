package com.example.loldetector.repository;

import com.example.loldetector.entity.SecurityAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityAlertRepository extends JpaRepository<SecurityAlert, Long> {

    List<SecurityAlert> findByAnalysisRunId(Long analysisId);

    List<SecurityAlert> findBySeverityIgnoreCase(String severity);

    @Query("SELECT s FROM SecurityAlert s WHERE LOWER(s.ruleName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.description) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.processName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<SecurityAlert> searchAlerts(@Param("query") String query);

    @Query("SELECT s FROM SecurityAlert s WHERE LOWER(s.severity) = LOWER(:severity) AND (LOWER(s.ruleName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.description) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.processName) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<SecurityAlert> filterAndSearchAlerts(@Param("severity") String severity, @Param("query") String query);

    List<SecurityAlert> findTop10ByOrderByDetectedAtDesc();

    Long countBySeverityIgnoreCase(String severity);
}
