package com.ideaforge.platform.iam.interfaces.rest;

import com.ideaforge.platform.iam.domain.model.commands.DeleteAccountCommand;
import com.ideaforge.platform.iam.domain.model.queries.GetAccountByIdQuery;
import com.ideaforge.platform.iam.domain.model.queries.GetAllAccountsQuery;
import com.ideaforge.platform.iam.domain.services.AccountsCommandService;
import com.ideaforge.platform.iam.domain.services.AccountsQueryService;
import com.ideaforge.platform.iam.interfaces.rest.resources.ChangePasswordResource;
import com.ideaforge.platform.iam.interfaces.rest.resources.UpdateAccountResource;
import com.ideaforge.platform.iam.interfaces.rest.transform.AccountResourceFromEntityAssembler;
import com.ideaforge.platform.iam.interfaces.rest.transform.ChangePasswordCommandFromResourceAssembler;
import com.ideaforge.platform.iam.interfaces.rest.transform.UpdateAccountCommandFromResourceAssembler;
import com.ideaforge.platform.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Accounts", description = "Account management endpoints")
public class AccountsController {
    private final AccountsQueryService accountsQueryService;
    private final AccountsCommandService accountsCommandService;
    public AccountsController(AccountsQueryService accountsQueryService, AccountsCommandService accountsCommandService) { this.accountsQueryService = accountsQueryService; this.accountsCommandService = accountsCommandService; }

    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        var resources = accountsQueryService.handle(new GetAllAccountsQuery()).stream().map(AccountResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable Long accountId) {
        var account = accountsQueryService.handle(new GetAccountByIdQuery(accountId));
        if (account.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(AccountResourceFromEntityAssembler.toResourceFromEntity(account.get()));
    }

    @PutMapping("/{accountId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long accountId, @Valid @RequestBody UpdateAccountResource resource) {
        var command = UpdateAccountCommandFromResourceAssembler.toCommandFromResource(accountId, resource);
        return ResponseEntity.ok(AccountResourceFromEntityAssembler.toResourceFromEntity(accountsCommandService.handle(command).orElseThrow()));
    }

    @PutMapping("/{accountId}/password")
    public ResponseEntity<?> changePassword(@PathVariable Long accountId, @Valid @RequestBody ChangePasswordResource resource) {
        accountsCommandService.handle(ChangePasswordCommandFromResourceAssembler.toCommandFromResource(accountId, resource));
        return ResponseEntity.ok(MessageResource.of("Password updated"));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long accountId) {
        accountsCommandService.handle(new DeleteAccountCommand(accountId));
        return ResponseEntity.ok(MessageResource.of("Account deleted"));
    }
}
