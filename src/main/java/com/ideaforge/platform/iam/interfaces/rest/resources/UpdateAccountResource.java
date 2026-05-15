package com.ideaforge.platform.iam.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateAccountResource(@NotBlank String status) { }
