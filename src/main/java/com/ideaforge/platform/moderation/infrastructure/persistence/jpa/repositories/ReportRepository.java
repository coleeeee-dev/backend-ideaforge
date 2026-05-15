package com.ideaforge.platform.moderation.infrastructure.persistence.jpa.repositories;

import com.ideaforge.platform.moderation.domain.model.aggregates.Report;
import com.ideaforge.platform.moderation.domain.model.valueobjects.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByStatus(ReportStatus status);
}
