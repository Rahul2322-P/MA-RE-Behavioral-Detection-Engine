package com.example.loldetector.repository;

import com.example.loldetector.entity.AnalysisRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisRunRepository extends JpaRepository<AnalysisRun, Long> {

    List<AnalysisRun> findAllByOrderByStartedAtDesc();

    @Query("SELECT COALESCE(SUM(a.totalEvents), 0) FROM AnalysisRun a")
    Long countTotalEventsProcessed();

    @Query("SELECT COALESCE(SUM(a.alertCount), 0) FROM AnalysisRun a")
    Long countTotalAlertsGenerated();
}
