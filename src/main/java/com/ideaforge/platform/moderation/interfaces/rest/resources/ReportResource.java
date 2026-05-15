package com.ideaforge.platform.moderation.interfaces.rest.resources;

public record ReportResource(Long id, Long reporterProfileId, String targetType, Long targetId, String reason, String description, String status, String decision, Long resolvedByAccountId) { }
