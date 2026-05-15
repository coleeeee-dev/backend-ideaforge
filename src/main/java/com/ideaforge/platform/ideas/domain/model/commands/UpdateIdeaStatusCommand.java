package com.ideaforge.platform.ideas.domain.model.commands;

public record UpdateIdeaStatusCommand(Long ideaId, String status) { }
