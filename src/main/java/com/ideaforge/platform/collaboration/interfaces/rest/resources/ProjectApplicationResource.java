package com.ideaforge.platform.collaboration.interfaces.rest.resources;

public record ProjectApplicationResource(Long id, Long ideaId, Long applicantProfileId, String requestedRole, String message, String status) { }
