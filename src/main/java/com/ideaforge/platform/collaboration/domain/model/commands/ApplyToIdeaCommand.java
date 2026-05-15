package com.ideaforge.platform.collaboration.domain.model.commands;

public record ApplyToIdeaCommand(Long ideaId, Long applicantProfileId, String requestedRole, String message) { }
