package com.ideaforge.platform.moderation.domain.services;

import com.ideaforge.platform.moderation.domain.model.aggregates.Report;
import com.ideaforge.platform.moderation.domain.model.commands.CreateReportCommand;
import com.ideaforge.platform.moderation.domain.model.commands.ResolveReportCommand;

import java.util.Optional;

public interface ReportsCommandService {
    Optional<Report> handle(CreateReportCommand command);
    Optional<Report> handle(ResolveReportCommand command);
}
