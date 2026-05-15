package com.ideaforge.platform.profiles.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileResource(@NotBlank String firstName, @NotBlank String lastName, String headline, String bio, String avatarUrl, String experienceLevel) { }
