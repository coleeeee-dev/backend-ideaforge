package com.ideaforge.platform.iam.application.internal.commandservices;

import com.ideaforge.platform.iam.domain.exceptions.EmailAlreadyExistsException;
import com.ideaforge.platform.iam.domain.exceptions.InvalidCredentialsException;
import com.ideaforge.platform.iam.domain.model.aggregates.Account;
import com.ideaforge.platform.iam.domain.model.commands.LoginCommand;
import com.ideaforge.platform.iam.domain.model.commands.RegisterAccountCommand;
import com.ideaforge.platform.iam.domain.model.valueobjects.LoginResult;
import com.ideaforge.platform.iam.domain.services.AuthService;
import com.ideaforge.platform.iam.infrastructure.persistence.jpa.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthCommandServiceImpl implements AuthService {
    private final AccountRepository accountRepository;

    public AuthCommandServiceImpl(AccountRepository accountRepository) { this.accountRepository = accountRepository; }

    @Override
    public Optional<Account> handle(RegisterAccountCommand command) {
        String email = command.email().trim().toLowerCase();
        if (accountRepository.existsByEmail(email)) throw new EmailAlreadyExistsException(email);
        var account = new Account(new RegisterAccountCommand(email, command.password()), hash(command.password()));
        return Optional.of(accountRepository.save(account));
    }

    @Override
    public Optional<LoginResult> handle(LoginCommand command) {
        String email = command.email().trim().toLowerCase();
        var account = accountRepository.findByEmail(email).orElseThrow(InvalidCredentialsException::new);
        if (!account.isActive() || !account.getPasswordHash().equals(hash(command.password()))) throw new InvalidCredentialsException();
        var token = "demo-token-" + UUID.randomUUID();
        return Optional.of(new LoginResult(account.getId(), account.getEmail(), account.getRole(), token));
    }

    public static String hash(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }
}
