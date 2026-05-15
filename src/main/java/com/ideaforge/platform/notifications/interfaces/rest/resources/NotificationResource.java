package com.ideaforge.platform.notifications.interfaces.rest.resources;

public record NotificationResource(Long id, Long recipientProfileId, String type, String title, String body, String readAt) { }
