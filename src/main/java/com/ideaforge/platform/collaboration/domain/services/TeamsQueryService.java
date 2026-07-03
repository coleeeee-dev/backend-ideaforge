package com.ideaforge.platform.collaboration.domain.services;

import com.ideaforge.platform.collaboration.domain.model.aggregates.Team;
import com.ideaforge.platform.collaboration.domain.model.queries.GetTeamContactsByIdeaIdQuery;
import com.ideaforge.platform.collaboration.domain.model.queries.GetTeamByIdeaIdQuery;
import com.ideaforge.platform.collaboration.domain.model.valueobjects.TeamContact;

import java.util.List;
import java.util.Optional;

public interface TeamsQueryService {
    Optional<Team> handle(GetTeamByIdeaIdQuery query);
    List<TeamContact> handle(GetTeamContactsByIdeaIdQuery query);
}
