package com.ideaforge.platform.collaboration.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record CreateConversationResource(@NotNull Long ideaId, Long projectApplicationId, @NotNull Long creatorProfileId, @NotNull Long applicantProfileId) { }
