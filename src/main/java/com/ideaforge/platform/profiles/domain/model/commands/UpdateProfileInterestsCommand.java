package com.ideaforge.platform.profiles.domain.model.commands;

import java.util.List;

public record UpdateProfileInterestsCommand(Long profileId, List<String> interests) { }
