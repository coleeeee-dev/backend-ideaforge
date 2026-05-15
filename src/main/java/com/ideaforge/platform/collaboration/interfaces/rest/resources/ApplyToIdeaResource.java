package com.ideaforge.platform.collaboration.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record ApplyToIdeaResource(@NotNull Long applicantProfileId, String requestedRole, String message) { }
