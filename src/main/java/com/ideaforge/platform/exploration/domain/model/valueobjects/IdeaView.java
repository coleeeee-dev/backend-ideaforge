package com.ideaforge.platform.exploration.domain.model.valueobjects;

public record IdeaView(Long id, Long creatorProfileId, String title, String shortDescription, String category, String status, String stage, String collaborationMode) { }
