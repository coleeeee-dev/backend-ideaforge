package com.ideaforge.platform.collaboration.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendMessageResource(@NotNull Long senderProfileId, @NotBlank String content) { }
