package com.ideaforge.platform.profiles.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateProfileResource(@NotNull Long accountId, @NotBlank String firstName, @NotBlank String lastName, String headline, String bio, String avatarUrl, String experienceLevel, List<String> skills, List<String> interests) { }
