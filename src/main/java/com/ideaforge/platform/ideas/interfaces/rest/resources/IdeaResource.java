package com.ideaforge.platform.ideas.interfaces.rest.resources;

import java.util.List;

public record IdeaResource(Long id, Long creatorProfileId, String title, String shortDescription, String description, String problem, String solution, String category, String status, String stage, String collaborationMode, String expectedCommitment, List<RequiredRoleResource> requiredRoles) { }
