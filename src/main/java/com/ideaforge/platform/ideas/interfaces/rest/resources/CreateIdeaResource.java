package com.ideaforge.platform.ideas.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateIdeaResource(@NotNull Long creatorProfileId, @NotBlank String title, String shortDescription, @NotBlank String description, String problem, String solution, String category, String stage, String collaborationMode, String expectedCommitment, List<CreateRequiredRoleResource> requiredRoles) { }
