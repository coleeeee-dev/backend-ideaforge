package com.ideaforge.platform.collaboration.domain.exceptions;

public class ConversationNotFoundException extends RuntimeException { public ConversationNotFoundException(Long id) { super("Conversation not found with id: " + id); } }
