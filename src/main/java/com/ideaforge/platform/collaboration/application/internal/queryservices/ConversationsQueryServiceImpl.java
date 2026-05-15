package com.ideaforge.platform.collaboration.application.internal.queryservices;

import com.ideaforge.platform.collaboration.domain.model.aggregates.Conversation;
import com.ideaforge.platform.collaboration.domain.model.entities.Message;
import com.ideaforge.platform.collaboration.domain.model.queries.GetConversationByIdQuery;
import com.ideaforge.platform.collaboration.domain.model.queries.GetMessagesByConversationIdQuery;
import com.ideaforge.platform.collaboration.domain.services.ConversationsQueryService;
import com.ideaforge.platform.collaboration.infrastructure.persistence.jpa.repositories.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationsQueryServiceImpl implements ConversationsQueryService {
    private final ConversationRepository repository;
    public ConversationsQueryServiceImpl(ConversationRepository repository) { this.repository = repository; }
    public Optional<Conversation> handle(GetConversationByIdQuery query) { return repository.findById(query.conversationId()); }
    public List<Message> handle(GetMessagesByConversationIdQuery query) { return repository.findById(query.conversationId()).map(Conversation::getMessages).orElse(List.of()); }
}
