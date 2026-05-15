package com.ideaforge.platform.exploration.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record SaveIdeaResource(@NotNull Long profileId, @NotNull Long ideaId) { }
