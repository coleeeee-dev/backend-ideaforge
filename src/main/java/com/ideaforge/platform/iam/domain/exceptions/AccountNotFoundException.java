package com.ideaforge.platform.iam.domain.exceptions;

public class AccountNotFoundException extends RuntimeException { public AccountNotFoundException(Long id) { super("Account not found with id: " + id); } }
