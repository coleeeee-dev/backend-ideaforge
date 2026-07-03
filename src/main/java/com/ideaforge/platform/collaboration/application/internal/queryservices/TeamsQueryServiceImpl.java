package com.ideaforge.platform.collaboration.application.internal.queryservices;

import com.ideaforge.platform.collaboration.application.internal.outboundservices.acl.ExternalIdeasService;
import com.ideaforge.platform.collaboration.application.internal.outboundservices.acl.ExternalProfilesService;
import com.ideaforge.platform.collaboration.domain.model.aggregates.Team;
import com.ideaforge.platform.collaboration.domain.model.entities.TeamMember;
import com.ideaforge.platform.collaboration.domain.model.queries.GetTeamContactsByIdeaIdQuery;
import com.ideaforge.platform.collaboration.domain.model.queries.GetTeamByIdeaIdQuery;
import com.ideaforge.platform.collaboration.domain.model.valueobjects.MemberStatus;
import com.ideaforge.platform.collaboration.domain.model.valueobjects.TeamContact;
import com.ideaforge.platform.collaboration.domain.services.TeamsQueryService;
import com.ideaforge.platform.profiles.domain.model.aggregates.Profile;
import com.ideaforge.platform.collaboration.infrastructure.persistence.jpa.repositories.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TeamsQueryServiceImpl implements TeamsQueryService {
    private final TeamRepository repository;
    private final ExternalIdeasService externalIdeasService;
    private final ExternalProfilesService externalProfilesService;
    public TeamsQueryServiceImpl(TeamRepository repository, ExternalIdeasService externalIdeasService, ExternalProfilesService externalProfilesService) {
        this.repository = repository;
        this.externalIdeasService = externalIdeasService;
        this.externalProfilesService = externalProfilesService;
    }
    public Optional<Team> handle(GetTeamByIdeaIdQuery query) { return repository.findByIdeaId(query.ideaId()); }

    public List<TeamContact> handle(GetTeamContactsByIdeaIdQuery query) {
        var idea = externalIdeasService.findById(query.ideaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Idea not found with id: " + query.ideaId()));
        var team = repository.findByIdeaId(query.ideaId()).orElse(null);
        var viewerIsOwner = idea.getCreatorProfileId().equals(query.viewerProfileId());
        var viewerIsActiveMember = team != null && team.hasActiveMember(query.viewerProfileId());
        if (!viewerIsOwner && !viewerIsActiveMember) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the idea owner or active team members can view team contacts");
        }

        var profileIds = new LinkedHashSet<Long>();
        profileIds.add(idea.getCreatorProfileId());
        if (team != null) {
            team.getMembers().stream()
                    .filter(member -> member.getMemberStatus() == MemberStatus.ACTIVE)
                    .map(TeamMember::getProfileId)
                    .filter(profileId -> !profileId.equals(idea.getCreatorProfileId()))
                    .forEach(profileIds::add);
        }

        Map<Long, Profile> profilesById = externalProfilesService.findAllById(new ArrayList<>(profileIds)).stream()
                .collect(Collectors.toMap(Profile::getId, Function.identity()));
        var contacts = new ArrayList<TeamContact>();
        contacts.add(toContact(idea.getCreatorProfileId(), "OWNER", profilesById.get(idea.getCreatorProfileId())));
        if (team != null) {
            team.getMembers().stream()
                    .filter(member -> member.getMemberStatus() == MemberStatus.ACTIVE)
                    .filter(member -> !member.getProfileId().equals(idea.getCreatorProfileId()))
                    .forEach(member -> contacts.add(toContact(member.getProfileId(), member.getRoleName(), profilesById.get(member.getProfileId()))));
        }
        return contacts;
    }

    private TeamContact toContact(Long profileId, String roleName, Profile profile) {
        var phoneNumber = profile != null && profile.isSharePhoneWithTeam() && profile.getPhoneNumber() != null ? profile.getPhoneNumber() : null;
        var contactAvailable = phoneNumber != null;
        return new TeamContact(
                profileId,
                profile == null ? null : profile.getFirstName(),
                profile == null ? null : profile.getLastName(),
                profile == null ? null : profile.getAvatarUrl(),
                roleName,
                phoneNumber,
                contactAvailable
        );
    }
}
