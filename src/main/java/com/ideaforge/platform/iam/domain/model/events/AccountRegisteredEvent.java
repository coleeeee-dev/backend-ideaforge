package com.ideaforge.platform.iam.domain.model.events;

public record AccountRegisteredEvent(Long accountId, String email) { }
