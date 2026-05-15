package com.ideaforge.platform.iam.domain.model.commands;

import com.ideaforge.platform.iam.domain.model.valueobjects.AccountStatus;

public record UpdateAccountCommand(Long accountId, AccountStatus status) { }
