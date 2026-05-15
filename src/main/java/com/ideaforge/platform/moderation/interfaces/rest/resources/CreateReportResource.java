package com.ideaforge.platform.moderation.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReportResource(@NotNull Long reporterProfileId, @NotBlank String targetType, @NotNull Long targetId, @NotBlank String reason, String description) { }
