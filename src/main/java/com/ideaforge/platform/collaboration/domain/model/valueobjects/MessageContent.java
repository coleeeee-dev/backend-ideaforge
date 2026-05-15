package com.ideaforge.platform.collaboration.domain.model.valueobjects;

public record MessageContent(String value) { public MessageContent { if (value == null || value.isBlank()) throw new IllegalArgumentException("Message cannot be blank"); } }
