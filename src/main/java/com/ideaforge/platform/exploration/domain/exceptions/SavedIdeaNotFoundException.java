package com.ideaforge.platform.exploration.domain.exceptions;

public class SavedIdeaNotFoundException extends RuntimeException { public SavedIdeaNotFoundException(Long profileId, Long ideaId) { super("Saved idea not found for profile " + profileId + " and idea " + ideaId); } }
