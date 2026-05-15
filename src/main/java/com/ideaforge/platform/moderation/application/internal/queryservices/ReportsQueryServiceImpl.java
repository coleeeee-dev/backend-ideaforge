package com.ideaforge.platform.moderation.application.internal.queryservices;

import com.ideaforge.platform.moderation.domain.model.aggregates.Report;
import com.ideaforge.platform.moderation.domain.model.queries.*;
import com.ideaforge.platform.moderation.domain.model.valueobjects.ReportStatus;
import com.ideaforge.platform.moderation.domain.services.ReportsQueryService;
import com.ideaforge.platform.moderation.infrastructure.persistence.jpa.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportsQueryServiceImpl implements ReportsQueryService {
    private final ReportRepository repository;
    public ReportsQueryServiceImpl(ReportRepository repository) { this.repository = repository; }
    public List<Report> handle(GetAllReportsQuery query) { return repository.findAll(); }
    public Optional<Report> handle(GetReportByIdQuery query) { return repository.findById(query.reportId()); }
    public List<Report> handle(GetReportsByStatusQuery query) { return repository.findByStatus(ReportStatus.valueOf(query.status().trim().toUpperCase())); }
}
