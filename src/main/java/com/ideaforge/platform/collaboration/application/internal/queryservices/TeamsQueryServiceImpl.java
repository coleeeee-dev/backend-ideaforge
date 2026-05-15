package com.ideaforge.platform.collaboration.application.internal.queryservices;

import com.ideaforge.platform.collaboration.domain.model.aggregates.Team;
import com.ideaforge.platform.collaboration.domain.model.queries.GetTeamByIdeaIdQuery;
import com.ideaforge.platform.collaboration.domain.services.TeamsQueryService;
import com.ideaforge.platform.collaboration.infrastructure.persistence.jpa.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamsQueryServiceImpl implements TeamsQueryService {
    private final TeamRepository repository;
    public TeamsQueryServiceImpl(TeamRepository repository) { this.repository = repository; }
    public Optional<Team> handle(GetTeamByIdeaIdQuery query) { return repository.findByIdeaId(query.ideaId()); }
}
