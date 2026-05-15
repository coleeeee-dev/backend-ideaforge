package com.ideaforge.platform.exploration.interfaces.rest.resources;

public record IdeaViewResource(Long id, Long creatorProfileId, String title, String shortDescription, String category, String status, String stage, String collaborationMode) { }
