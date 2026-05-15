package com.ideaforge.platform.profiles.domain.services;

import com.ideaforge.platform.profiles.domain.model.aggregates.Profile;
import com.ideaforge.platform.profiles.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface ProfilesQueryService {
    List<Profile> handle(GetAllProfilesQuery query);
    Optional<Profile> handle(GetProfileByIdQuery query);
    Optional<Profile> handle(GetProfileByAccountIdQuery query);
}
