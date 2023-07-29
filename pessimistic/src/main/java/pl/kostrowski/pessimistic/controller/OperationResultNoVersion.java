package pl.kostrowski.pessimistic.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kostrowski.pessimistic.model.audited.Account;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationResultNoVersion {
    private Account account;
    private Integer retryCount;
}
