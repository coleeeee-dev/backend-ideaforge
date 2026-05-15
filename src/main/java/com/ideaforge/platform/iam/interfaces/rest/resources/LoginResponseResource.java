package com.ideaforge.platform.iam.interfaces.rest.resources;

public record LoginResponseResource(Long accountId, String email, String role, String token) { }
