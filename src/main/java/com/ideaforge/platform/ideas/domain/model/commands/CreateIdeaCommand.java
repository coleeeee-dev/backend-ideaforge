package com.ideaforge.platform.ideas.domain.model.commands;

import java.util.List;

public record CreateIdeaCommand(Long creatorProfileId, String title, String shortDescription, String description, String problem, String solution, String category, String stage, String collaborationMode, String expectedCommitment, List<CreateRequiredRoleCommand> requiredRoles) { }
