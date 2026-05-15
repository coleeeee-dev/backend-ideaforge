package com.ideaforge.platform.profiles.domain.model.commands;

import java.util.List;

public record CreateProfileCommand(Long accountId, String firstName, String lastName, String headline, String bio, String avatarUrl, String experienceLevel, List<String> skills, List<String> interests) { }
