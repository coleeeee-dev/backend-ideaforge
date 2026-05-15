package com.ideaforge.platform.collaboration.domain.model.commands;

public record SendMessageCommand(Long conversationId, Long senderProfileId, String content) { }
