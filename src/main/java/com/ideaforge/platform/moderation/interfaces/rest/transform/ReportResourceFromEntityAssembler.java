package com.ideaforge.platform.moderation.interfaces.rest.transform;

import com.ideaforge.platform.moderation.domain.model.aggregates.Report;
import com.ideaforge.platform.moderation.interfaces.rest.resources.ReportResource;

public class ReportResourceFromEntityAssembler { public static ReportResource toResourceFromEntity(Report e) { return new ReportResource(e.getId(), e.getReporterProfileId(), e.getTargetType().name(), e.getTargetId(), e.getReason().name(), e.getDescription(), e.getStatus().name(), e.getDecision() == null ? null : e.getDecision().name(), e.getResolvedByAccountId()); } }
