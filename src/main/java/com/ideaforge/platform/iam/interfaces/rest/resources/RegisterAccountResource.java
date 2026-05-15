package com.ideaforge.platform.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterAccountResource(@NotBlank @Email String email, @NotBlank @Size(min = 6) String password) { }
