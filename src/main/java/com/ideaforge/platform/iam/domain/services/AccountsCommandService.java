package com.ideaforge.platform.iam.domain.services;

import com.ideaforge.platform.iam.domain.model.aggregates.Account;
import com.ideaforge.platform.iam.domain.model.commands.ChangePasswordCommand;
import com.ideaforge.platform.iam.domain.model.commands.DeleteAccountCommand;
import com.ideaforge.platform.iam.domain.model.commands.UpdateAccountCommand;

import java.util.Optional;

public interface AccountsCommandService {
    Optional<Account> handle(UpdateAccountCommand command);
    void handle(ChangePasswordCommand command);
    void handle(DeleteAccountCommand command);
}
