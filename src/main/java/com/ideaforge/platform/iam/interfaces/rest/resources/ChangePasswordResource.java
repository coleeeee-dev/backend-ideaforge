package com.ideaforge.platform.iam.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordResource(@NotBlank String currentPassword, @NotBlank @Size(min = 6) String newPassword) { }
