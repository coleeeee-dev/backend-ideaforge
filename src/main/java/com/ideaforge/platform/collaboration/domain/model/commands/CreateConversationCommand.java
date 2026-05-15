package com.ideaforge.platform.collaboration.domain.model.commands;

public record CreateConversationCommand(Long ideaId, Long projectApplicationId, Long creatorProfileId, Long applicantProfileId) { }
