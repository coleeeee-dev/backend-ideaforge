package com.ideaforge.platform.iam.interfaces.rest.transform;

import com.ideaforge.platform.iam.domain.model.aggregates.Account;
import com.ideaforge.platform.iam.interfaces.rest.resources.AccountResource;

public class AccountResourceFromEntityAssembler { public static AccountResource toResourceFromEntity(Account entity) { return new AccountResource(entity.getId(), entity.getEmail(), entity.getRole().name(), entity.getStatus().name()); } }
