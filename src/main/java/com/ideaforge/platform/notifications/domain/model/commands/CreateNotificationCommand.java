package com.ideaforge.platform.notifications.domain.model.commands;

public record CreateNotificationCommand(Long recipientProfileId, String type, String title, String body) { }
