package com.ideaforge.platform.exploration.application.internal.queryservices;

import com.ideaforge.platform.exploration.domain.model.aggregates.SavedIdea;
import com.ideaforge.platform.exploration.domain.model.queries.*;
import com.ideaforge.platform.exploration.domain.model.valueobjects.IdeaView;
import com.ideaforge.platform.exploration.domain.services.ExplorationQueryService;
import com.ideaforge.platform.exploration.infrastructure.persistence.jpa.repositories.SavedIdeaRepository;
import com.ideaforge.platform.ideas.domain.model.aggregates.Idea;
import com.ideaforge.platform.ideas.infrastructure.persistence.jpa.repositories.IdeaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExplorationQueryServiceImpl implements ExplorationQueryService {
    private final IdeaRepository ideaRepository;
    private final SavedIdeaRepository savedIdeaRepository;
    public ExplorationQueryServiceImpl(IdeaRepository ideaRepository, SavedIdeaRepository savedIdeaRepository) { this.ideaRepository = ideaRepository; this.savedIdeaRepository = savedIdeaRepository; }

    public List<IdeaView> handle(SearchIdeasQuery query) {
        return ideaRepository.findAll().stream()
                .filter(i -> query.keyword() == null || query.keyword().isBlank() || contains(i.getTitle(), query.keyword()) || contains(i.getDescription(), query.keyword()))
                .filter(i -> query.category() == null || query.category().isBlank() || i.getCategory().name().equalsIgnoreCase(query.category()))
                .filter(i -> query.status() == null || query.status().isBlank() || i.getStatus().name().equalsIgnoreCase(query.status()))
                .filter(i -> query.stage() == null || query.stage().isBlank() || i.getStage().name().equalsIgnoreCase(query.stage()))
                .map(this::toView)
                .toList();
    }

    public List<IdeaView> handle(GetRecommendedIdeasQuery query) { return ideaRepository.findAll().stream().limit(10).map(this::toView).toList(); }
    public List<SavedIdea> handle(GetSavedIdeasByProfileIdQuery query) { return savedIdeaRepository.findByProfileId(query.profileId()); }
    private boolean contains(String text, String keyword) { return text != null && text.toLowerCase().contains(keyword.toLowerCase()); }
    private IdeaView toView(Idea i) { return new IdeaView(i.getId(), i.getCreatorProfileId(), i.getTitle(), i.getShortDescription(), i.getCategory().name(), i.getStatus().name(), i.getStage().name(), i.getCollaborationMode().name()); }
}
