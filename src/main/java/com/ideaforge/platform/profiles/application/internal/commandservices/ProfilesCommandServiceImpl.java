package com.ideaforge.platform.profiles.application.internal.commandservices;

import com.ideaforge.platform.profiles.domain.exceptions.ProfileNotFoundException;
import com.ideaforge.platform.profiles.domain.model.aggregates.Profile;
import com.ideaforge.platform.profiles.domain.model.commands.*;
import com.ideaforge.platform.profiles.domain.services.ProfilesCommandService;
import com.ideaforge.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfilesCommandServiceImpl implements ProfilesCommandService {
    private final ProfileRepository profileRepository;
    public ProfilesCommandServiceImpl(ProfileRepository profileRepository) { this.profileRepository = profileRepository; }

    public Optional<Profile> handle(CreateProfileCommand command) {
        if (profileRepository.existsByAccountId(command.accountId())) throw new IllegalArgumentException("Profile already exists for account: " + command.accountId());
        return Optional.of(profileRepository.save(new Profile(command)));
    }
    public Optional<Profile> handle(UpdateProfileCommand command) {
        var profile = profileRepository.findById(command.profileId()).orElseThrow(() -> new ProfileNotFoundException(command.profileId()));
        profile.update(command);
        return Optional.of(profileRepository.save(profile));
    }
    public Optional<Profile> handle(UpdateProfileSkillsCommand command) {
        var profile = profileRepository.findById(command.profileId()).orElseThrow(() -> new ProfileNotFoundException(command.profileId()));
        profile.updateSkills(command.skills());
        return Optional.of(profileRepository.save(profile));
    }
    public Optional<Profile> handle(UpdateProfileInterestsCommand command) {
        var profile = profileRepository.findById(command.profileId()).orElseThrow(() -> new ProfileNotFoundException(command.profileId()));
        profile.updateInterests(command.interests());
        return Optional.of(profileRepository.save(profile));
    }
    public Optional<Profile> handle(UpdateContactSettingsCommand command) {
        var profile = profileRepository.findById(command.profileId()).orElseThrow(() -> new ProfileNotFoundException(command.profileId()));
        profile.updateContactSettings(command.phoneNumber(), command.sharePhoneWithTeam());
        return Optional.of(profileRepository.save(profile));
    }
}
