package com.ideaforge.platform.profiles.domain.services;

import com.ideaforge.platform.profiles.domain.model.aggregates.Profile;
import com.ideaforge.platform.profiles.domain.model.commands.*;

import java.util.Optional;

public interface ProfilesCommandService {
    Optional<Profile> handle(CreateProfileCommand command);
    Optional<Profile> handle(UpdateProfileCommand command);
    Optional<Profile> handle(UpdateProfileSkillsCommand command);
    Optional<Profile> handle(UpdateProfileInterestsCommand command);
}
