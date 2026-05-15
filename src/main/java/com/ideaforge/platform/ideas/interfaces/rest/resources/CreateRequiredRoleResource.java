package com.ideaforge.platform.ideas.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record CreateRequiredRoleResource(@NotBlank String roleName, String description, Integer quantity, String requiredExperienceLevel) { }
