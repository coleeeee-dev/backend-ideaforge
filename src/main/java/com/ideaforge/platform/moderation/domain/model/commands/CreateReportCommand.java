package com.ideaforge.platform.moderation.domain.model.commands;

public record CreateReportCommand(Long reporterProfileId, String targetType, Long targetId, String reason, String description) { }
