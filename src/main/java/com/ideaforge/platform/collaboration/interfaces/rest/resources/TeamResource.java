package com.ideaforge.platform.collaboration.interfaces.rest.resources;

import java.util.List;

public record TeamResource(Long id, Long ideaId, String name, String status, List<TeamMemberResource> members) { }
