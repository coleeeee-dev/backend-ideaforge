package com.ideaforge.platform.collaboration.domain.services;

import com.ideaforge.platform.collaboration.domain.model.aggregates.Conversation;
import com.ideaforge.platform.collaboration.domain.model.commands.CreateConversationCommand;
import com.ideaforge.platform.collaboration.domain.model.commands.SendMessageCommand;

import java.util.Optional;

public interface ConversationsCommandService {
    Optional<Conversation> handle(CreateConversationCommand command);
    Optional<Conversation> handle(SendMessageCommand command);
}
