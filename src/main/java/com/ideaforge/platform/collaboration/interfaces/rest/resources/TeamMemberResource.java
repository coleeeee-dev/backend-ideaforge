package com.ideaforge.platform.collaboration.interfaces.rest.resources;

public record TeamMemberResource(Long id, Long profileId, String roleName, String memberStatus) { }
