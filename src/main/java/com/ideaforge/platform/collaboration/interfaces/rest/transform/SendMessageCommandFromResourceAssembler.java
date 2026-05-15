package com.ideaforge.platform.collaboration.interfaces.rest.transform;

import com.ideaforge.platform.collaboration.domain.model.commands.SendMessageCommand;
import com.ideaforge.platform.collaboration.interfaces.rest.resources.SendMessageResource;

public class SendMessageCommandFromResourceAssembler { public static SendMessageCommand toCommandFromResource(Long conversationId, SendMessageResource r) { return new SendMessageCommand(conversationId, r.senderProfileId(), r.content()); } }
