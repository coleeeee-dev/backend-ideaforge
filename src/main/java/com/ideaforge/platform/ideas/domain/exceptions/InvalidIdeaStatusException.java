package com.ideaforge.platform.ideas.domain.exceptions;

public class InvalidIdeaStatusException extends RuntimeException { public InvalidIdeaStatusException(String status) { super("Invalid idea status: " + status); } }
