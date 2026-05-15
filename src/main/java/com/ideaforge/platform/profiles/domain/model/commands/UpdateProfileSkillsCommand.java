package com.ideaforge.platform.profiles.domain.model.commands;

import java.util.List;

public record UpdateProfileSkillsCommand(Long profileId, List<String> skills) { }
