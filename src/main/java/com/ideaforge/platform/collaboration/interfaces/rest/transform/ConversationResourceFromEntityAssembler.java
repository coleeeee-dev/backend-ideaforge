package com.ideaforge.platform.collaboration.interfaces.rest.transform;

import com.ideaforge.platform.collaboration.domain.model.aggregates.Conversation;
import com.ideaforge.platform.collaboration.interfaces.rest.resources.*;

public class ConversationResourceFromEntityAssembler {
    public static ConversationResource toResourceFromEntity(Conversation e) {
        var messages = e.getMessages().stream().map(m -> new MessageResource(m.getId(), m.getSenderProfileId(), m.getContent(), m.getSentAt().toString())).toList();
        return new ConversationResource(e.getId(), e.getIdeaId(), e.getProjectApplicationId(), e.getCreatorProfileId(), e.getApplicantProfileId(), messages);
    }
}
