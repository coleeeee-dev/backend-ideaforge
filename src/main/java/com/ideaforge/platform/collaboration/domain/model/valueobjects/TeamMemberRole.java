package com.ideaforge.platform.collaboration.domain.model.valueobjects;

public record TeamMemberRole(String value) { public TeamMemberRole { if (value == null || value.isBlank()) value = "Collaborator"; } }
