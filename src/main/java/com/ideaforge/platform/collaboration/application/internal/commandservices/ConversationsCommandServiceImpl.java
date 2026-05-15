package com.ideaforge.platform.collaboration.application.internal.commandservices;

import com.ideaforge.platform.collaboration.domain.exceptions.ConversationNotFoundException;
import com.ideaforge.platform.collaboration.domain.model.aggregates.Conversation;
import com.ideaforge.platform.collaboration.domain.model.commands.CreateConversationCommand;
import com.ideaforge.platform.collaboration.domain.model.commands.SendMessageCommand;
import com.ideaforge.platform.collaboration.domain.services.ConversationsCommandService;
import com.ideaforge.platform.collaboration.infrastructure.persistence.jpa.repositories.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationsCommandServiceImpl implements ConversationsCommandService {
    private final ConversationRepository repository;
    public ConversationsCommandServiceImpl(ConversationRepository repository) { this.repository = repository; }
    public Optional<Conversation> handle(CreateConversationCommand command) { return Optional.of(repository.save(new Conversation(command))); }
    public Optional<Conversation> handle(SendMessageCommand command) { var conversation = repository.findById(command.conversationId()).orElseThrow(() -> new ConversationNotFoundException(command.conversationId())); conversation.addMessage(command.senderProfileId(), command.content()); return Optional.of(repository.save(conversation)); }
}
