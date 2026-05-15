package com.ideaforge.platform.moderation.application.internal.commandservices;

import com.ideaforge.platform.moderation.domain.exceptions.ReportNotFoundException;
import com.ideaforge.platform.moderation.domain.model.aggregates.Report;
import com.ideaforge.platform.moderation.domain.model.commands.*;
import com.ideaforge.platform.moderation.domain.services.ReportsCommandService;
import com.ideaforge.platform.moderation.infrastructure.persistence.jpa.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportsCommandServiceImpl implements ReportsCommandService {
    private final ReportRepository repository;
    public ReportsCommandServiceImpl(ReportRepository repository) { this.repository = repository; }
    public Optional<Report> handle(CreateReportCommand command) { return Optional.of(repository.save(new Report(command))); }
    public Optional<Report> handle(ResolveReportCommand command) { var report = repository.findById(command.reportId()).orElseThrow(() -> new ReportNotFoundException(command.reportId())); report.resolve(command); return Optional.of(repository.save(report)); }
}
