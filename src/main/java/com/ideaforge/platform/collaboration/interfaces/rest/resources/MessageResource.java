package com.ideaforge.platform.collaboration.interfaces.rest.resources;

public record MessageResource(Long id, Long senderProfileId, String content, String sentAt) { }
