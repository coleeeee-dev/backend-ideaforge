package com.ideaforge.platform.collaboration.application.internal.outboundservices.acl;

import com.ideaforge.platform.profiles.domain.model.aggregates.Profile;
import com.ideaforge.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExternalProfilesService {
    private final ProfileRepository profileRepository;

    public ExternalProfilesService(ProfileRepository profileRepository) { this.profileRepository = profileRepository; }

    public Optional<Profile> findById(Long profileId) { return profileRepository.findById(profileId); }

    public List<Profile> findAllById(List<Long> profileIds) { return profileRepository.findAllById(profileIds); }
}
