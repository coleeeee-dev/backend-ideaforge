package com.ideaforge.platform.exploration.domain.services;

import com.ideaforge.platform.exploration.domain.model.aggregates.SavedIdea;
import com.ideaforge.platform.exploration.domain.model.queries.GetRecommendedIdeasQuery;
import com.ideaforge.platform.exploration.domain.model.queries.GetSavedIdeasByProfileIdQuery;
import com.ideaforge.platform.exploration.domain.model.queries.SearchIdeasQuery;
import com.ideaforge.platform.exploration.domain.model.valueobjects.IdeaView;

import java.util.List;

public interface ExplorationQueryService {
    List<IdeaView> handle(SearchIdeasQuery query);
    List<IdeaView> handle(GetRecommendedIdeasQuery query);
    List<SavedIdea> handle(GetSavedIdeasByProfileIdQuery query);
}
