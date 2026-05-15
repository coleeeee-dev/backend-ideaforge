package com.ideaforge.platform.moderation.interfaces.rest.transform;

import com.ideaforge.platform.moderation.domain.model.commands.CreateReportCommand;
import com.ideaforge.platform.moderation.interfaces.rest.resources.CreateReportResource;

public class CreateReportCommandFromResourceAssembler { public static CreateReportCommand toCommandFromResource(CreateReportResource r) { return new CreateReportCommand(r.reporterProfileId(), r.targetType(), r.targetId(), r.reason(), r.description()); } }
