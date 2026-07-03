package com.ideaforge.platform.collaboration.interfaces.rest.resources;

public record TeamContactResource(Long profileId, String firstName, String lastName, String avatarUrl, String roleName, String phoneNumber, boolean contactAvailable) { }
