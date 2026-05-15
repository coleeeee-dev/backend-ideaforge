package com.ideaforge.platform.moderation.domain.exceptions;

public class ReportNotFoundException extends RuntimeException { public ReportNotFoundException(Long id) { super("Report not found with id: " + id); } }
