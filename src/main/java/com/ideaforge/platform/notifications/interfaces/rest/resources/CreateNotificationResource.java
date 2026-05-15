package com.ideaforge.platform.notifications.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateNotificationResource(@NotNull Long recipientProfileId, @NotBlank String type, @NotBlank String title, @NotBlank String body) { }
