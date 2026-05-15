package com.ideaforge.platform.ideas.domain.model.commands;

public record UpdateIdeaCommand(Long ideaId, String title, String shortDescription, String description, String problem, String solution, String category, String collaborationMode, String expectedCommitment) { }
