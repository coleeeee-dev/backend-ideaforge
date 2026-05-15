package com.ideaforge.platform.iam.domain.services;

import com.ideaforge.platform.iam.domain.model.aggregates.Account;
import com.ideaforge.platform.iam.domain.model.queries.GetAccountByEmailQuery;
import com.ideaforge.platform.iam.domain.model.queries.GetAccountByIdQuery;
import com.ideaforge.platform.iam.domain.model.queries.GetAllAccountsQuery;

import java.util.List;
import java.util.Optional;

public interface AccountsQueryService {
    List<Account> handle(GetAllAccountsQuery query);
    Optional<Account> handle(GetAccountByIdQuery query);
    Optional<Account> handle(GetAccountByEmailQuery query);
}
