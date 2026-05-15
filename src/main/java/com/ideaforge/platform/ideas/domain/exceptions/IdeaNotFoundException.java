package com.ideaforge.platform.ideas.domain.exceptions;

public class IdeaNotFoundException extends RuntimeException { public IdeaNotFoundException(Long id) { super("Idea not found with id: " + id); } }
