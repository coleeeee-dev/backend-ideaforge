package com.ideaforge.platform.collaboration.domain.exceptions;

public class TeamNotFoundException extends RuntimeException { public TeamNotFoundException(Long id) { super("Team not found with id: " + id); } }
