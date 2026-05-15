package com.ideaforge.platform.profiles.interfaces.rest.resources;

import java.util.List;

public record ProfileResource(Long id, Long accountId, String firstName, String lastName, String headline, String bio, String avatarUrl, String experienceLevel, List<SkillResource> skills, List<InterestResource> interests) { }
