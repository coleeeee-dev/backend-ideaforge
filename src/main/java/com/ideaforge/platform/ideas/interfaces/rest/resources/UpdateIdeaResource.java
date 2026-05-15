package com.ideaforge.platform.ideas.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateIdeaResource(@NotBlank String title, String shortDescription, @NotBlank String description, String problem, String solution, String category, String collaborationMode, String expectedCommitment) { }
