package com.ideaforge.platform.ideas.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateIdeaStatusResource(@NotBlank String status) { }
