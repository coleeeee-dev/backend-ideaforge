package com.ideaforge.platform.ideas.domain.model.commands;

public record AddRequiredRoleCommand(Long ideaId, String roleName, String description, Integer quantity, String requiredExperienceLevel) { }
