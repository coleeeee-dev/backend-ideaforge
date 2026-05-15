package com.ideaforge.platform.iam.application.internal.queryservices;

import com.ideaforge.platform.iam.domain.model.aggregates.Account;
import com.ideaforge.platform.iam.domain.model.queries.GetAccountByEmailQuery;
import com.ideaforge.platform.iam.domain.model.queries.GetAccountByIdQuery;
import com.ideaforge.platform.iam.domain.model.queries.GetAllAccountsQuery;
import com.ideaforge.platform.iam.domain.services.AccountsQueryService;
import com.ideaforge.platform.iam.infrastructure.persistence.jpa.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountsQueryServiceImpl implements AccountsQueryService {
    private final AccountRepository accountRepository;
    public AccountsQueryServiceImpl(AccountRepository accountRepository) { this.accountRepository = accountRepository; }
    public List<Account> handle(GetAllAccountsQuery query) { return accountRepository.findAll(); }
    public Optional<Account> handle(GetAccountByIdQuery query) { return accountRepository.findById(query.accountId()); }
    public Optional<Account> handle(GetAccountByEmailQuery query) { return accountRepository.findByEmail(query.email().trim().toLowerCase()); }
}
