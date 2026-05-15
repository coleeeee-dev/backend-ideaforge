package com.ideaforge.platform.moderation.domain.model.commands;

public record ResolveReportCommand(Long reportId, Long resolvedByAccountId, String status, String decision) { }
