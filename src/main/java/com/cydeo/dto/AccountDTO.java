package com.cydeo.dto;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;

    @NotNull
    @Positive
    private BigDecimal balance;

    @NotNull
    private AccountType accountType;

    private Date creationDate;

    @NotNull
    private Long userId;

    private AccountStatus accountStatus;

    public AccountDTO(BigDecimal balance, AccountType accountType, Date creationDate, Long userId) {
        this.balance = balance;
        this.accountType = accountType;
        this.creationDate = creationDate;
        this.userId = userId;
    }
}




