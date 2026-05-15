package com.ideaforge.platform.ideas.domain.model.commands;

public record CreateRequiredRoleCommand(String roleName, String description, Integer quantity, String requiredExperienceLevel) { }
