package com.ideaforge.platform.profiles.application.internal.queryservices;

import com.ideaforge.platform.profiles.domain.model.aggregates.Profile;
import com.ideaforge.platform.profiles.domain.model.queries.*;
import com.ideaforge.platform.profiles.domain.services.ProfilesQueryService;
import com.ideaforge.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfilesQueryServiceImpl implements ProfilesQueryService {
    private final ProfileRepository profileRepository;
    public ProfilesQueryServiceImpl(ProfileRepository profileRepository) { this.profileRepository = profileRepository; }
    public List<Profile> handle(GetAllProfilesQuery query) { return profileRepository.findAll(); }
    public Optional<Profile> handle(GetProfileByIdQuery query) { return profileRepository.findById(query.profileId()); }
    public Optional<Profile> handle(GetProfileByAccountIdQuery query) { return profileRepository.findByAccountId(query.accountId()); }
}
