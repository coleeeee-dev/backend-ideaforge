package com.ideaforge.platform.iam.domain.model.commands;

public record ChangePasswordCommand(Long accountId, String currentPassword, String newPassword) { }
