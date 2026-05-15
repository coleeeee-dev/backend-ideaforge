package com.ideaforge.platform.moderation.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResolveReportResource(@NotNull Long resolvedByAccountId, @NotBlank String status, @NotBlank String decision) { }
