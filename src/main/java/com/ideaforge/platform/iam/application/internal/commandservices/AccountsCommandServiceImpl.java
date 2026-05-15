package com.ideaforge.platform.iam.application.internal.commandservices;

import com.ideaforge.platform.iam.domain.exceptions.AccountNotFoundException;
import com.ideaforge.platform.iam.domain.exceptions.InvalidCredentialsException;
import com.ideaforge.platform.iam.domain.model.aggregates.Account;
import com.ideaforge.platform.iam.domain.model.commands.ChangePasswordCommand;
import com.ideaforge.platform.iam.domain.model.commands.DeleteAccountCommand;
import com.ideaforge.platform.iam.domain.model.commands.UpdateAccountCommand;
import com.ideaforge.platform.iam.domain.services.AccountsCommandService;
import com.ideaforge.platform.iam.infrastructure.persistence.jpa.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountsCommandServiceImpl implements AccountsCommandService {
    private final AccountRepository accountRepository;

    public AccountsCommandServiceImpl(AccountRepository accountRepository) { this.accountRepository = accountRepository; }

    @Override
    public Optional<Account> handle(UpdateAccountCommand command) {
        var account = accountRepository.findById(command.accountId()).orElseThrow(() -> new AccountNotFoundException(command.accountId()));
        account.updateStatus(command.status());
        return Optional.of(accountRepository.save(account));
    }

    @Override
    public void handle(ChangePasswordCommand command) {
        var account = accountRepository.findById(command.accountId()).orElseThrow(() -> new AccountNotFoundException(command.accountId()));
        if (!account.getPasswordHash().equals(AuthCommandServiceImpl.hash(command.currentPassword()))) throw new InvalidCredentialsException();
        account.changePassword(AuthCommandServiceImpl.hash(command.newPassword()));
        accountRepository.save(account);
    }

    @Override
    public void handle(DeleteAccountCommand command) {
        if (!accountRepository.existsById(command.accountId())) throw new AccountNotFoundException(command.accountId());
        accountRepository.deleteById(command.accountId());
    }
}
