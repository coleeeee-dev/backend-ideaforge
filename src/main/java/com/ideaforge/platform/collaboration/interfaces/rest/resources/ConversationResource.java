package com.ideaforge.platform.collaboration.interfaces.rest.resources;

import java.util.List;

public record ConversationResource(Long id, Long ideaId, Long projectApplicationId, Long creatorProfileId, Long applicantProfileId, List<MessageResource> messages) { }
