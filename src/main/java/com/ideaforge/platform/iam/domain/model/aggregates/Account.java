package com.ideaforge.platform.iam.domain.model.aggregates;

import com.ideaforge.platform.iam.domain.model.commands.RegisterAccountCommand;
import com.ideaforge.platform.iam.domain.model.valueobjects.AccountRole;
import com.ideaforge.platform.iam.domain.model.valueobjects.AccountStatus;
import com.ideaforge.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Account extends AuditableAbstractAggregateRoot<Account> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountRole role = AccountRole.USER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status = AccountStatus.ACTIVE;

    public Account(RegisterAccountCommand command, String passwordHash) {
        this.email = command.email().trim().toLowerCase();
        this.passwordHash = passwordHash;
        this.role = AccountRole.USER;
        this.status = AccountStatus.ACTIVE;
    }

    public void changePassword(String newPasswordHash) { this.passwordHash = newPasswordHash; }
    public void updateStatus(AccountStatus status) { this.status = status; }
    public boolean isActive() { return this.status == AccountStatus.ACTIVE; }
}
