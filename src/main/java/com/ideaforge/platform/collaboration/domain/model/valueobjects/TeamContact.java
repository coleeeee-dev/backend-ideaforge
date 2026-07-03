package com.ideaforge.platform.collaboration.domain.model.valueobjects;

public record TeamContact(Long profileId, String firstName, String lastName, String avatarUrl, String roleName, String phoneNumber, boolean contactAvailable) { }
