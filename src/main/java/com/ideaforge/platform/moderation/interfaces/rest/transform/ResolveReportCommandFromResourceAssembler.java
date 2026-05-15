package com.ideaforge.platform.moderation.interfaces.rest.transform;

import com.ideaforge.platform.moderation.domain.model.commands.ResolveReportCommand;
import com.ideaforge.platform.moderation.interfaces.rest.resources.ResolveReportResource;

public class ResolveReportCommandFromResourceAssembler { public static ResolveReportCommand toCommandFromResource(Long id, ResolveReportResource r) { return new ResolveReportCommand(id, r.resolvedByAccountId(), r.status(), r.decision()); } }
