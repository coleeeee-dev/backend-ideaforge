package com.ideaforge.platform.moderation.domain.services;

import com.ideaforge.platform.moderation.domain.model.aggregates.Report;
import com.ideaforge.platform.moderation.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface ReportsQueryService {
    List<Report> handle(GetAllReportsQuery query);
    Optional<Report> handle(GetReportByIdQuery query);
    List<Report> handle(GetReportsByStatusQuery query);
}
