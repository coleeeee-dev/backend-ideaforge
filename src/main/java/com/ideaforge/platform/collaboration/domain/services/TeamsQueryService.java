package com.ideaforge.platform.collaboration.domain.services;

import com.ideaforge.platform.collaboration.domain.model.aggregates.Team;
import com.ideaforge.platform.collaboration.domain.model.queries.GetTeamByIdeaIdQuery;

import java.util.Optional;

public interface TeamsQueryService { Optional<Team> handle(GetTeamByIdeaIdQuery query); }
