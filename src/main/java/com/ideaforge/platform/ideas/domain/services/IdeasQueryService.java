package com.ideaforge.platform.ideas.domain.services;

import com.ideaforge.platform.ideas.domain.model.aggregates.Idea;
import com.ideaforge.platform.ideas.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface IdeasQueryService {
    List<Idea> handle(GetAllIdeasQuery query);
    List<Idea> handle(GetIdeasQuery query);
    Optional<Idea> handle(GetIdeaByIdQuery query);
    List<Idea> handle(GetIdeasByCreatorIdQuery query);
    List<Idea> handle(GetIdeasByStatusQuery query);
}
