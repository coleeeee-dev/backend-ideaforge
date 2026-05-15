package com.ideaforge.platform.collaboration.domain.services;

import com.ideaforge.platform.collaboration.domain.model.aggregates.Conversation;
import com.ideaforge.platform.collaboration.domain.model.entities.Message;
import com.ideaforge.platform.collaboration.domain.model.queries.GetConversationByIdQuery;
import com.ideaforge.platform.collaboration.domain.model.queries.GetMessagesByConversationIdQuery;

import java.util.List;
import java.util.Optional;

public interface ConversationsQueryService {
    Optional<Conversation> handle(GetConversationByIdQuery query);
    List<Message> handle(GetMessagesByConversationIdQuery query);
}
