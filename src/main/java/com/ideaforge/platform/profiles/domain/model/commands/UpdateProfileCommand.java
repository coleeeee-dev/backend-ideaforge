package com.ideaforge.platform.profiles.domain.model.commands;

public record UpdateProfileCommand(Long profileId, String firstName, String lastName, String headline, String bio, String avatarUrl, String experienceLevel) { }
