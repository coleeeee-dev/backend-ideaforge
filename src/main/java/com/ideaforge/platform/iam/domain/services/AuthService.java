package com.ideaforge.platform.iam.domain.services;

import com.ideaforge.platform.iam.domain.model.aggregates.Account;
import com.ideaforge.platform.iam.domain.model.commands.LoginCommand;
import com.ideaforge.platform.iam.domain.model.commands.RegisterAccountCommand;
import com.ideaforge.platform.iam.domain.model.valueobjects.LoginResult;

import java.util.Optional;

public interface AuthService {
    Optional<Account> handle(RegisterAccountCommand command);
    Optional<LoginResult> handle(LoginCommand command);
}
