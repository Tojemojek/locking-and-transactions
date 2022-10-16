package pl.kostrowski.locking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import pl.kostrowski.locking.dto.AccountDto;
import pl.kostrowski.locking.mapper.AccountMapper;
import pl.kostrowski.locking.model.Account;
import pl.kostrowski.locking.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountServiceTransactional ast;
    private final AccountRepository repository;
    private final AccountMapper accountMapper;

    public void addToAccount(String bid, BigDecimal value, boolean retry, AtomicInteger atomicInteger) {
        if (retry) {
            addWithRetry(bid, value, atomicInteger);
        } else {
            ast.addOnce(bid, value);
        }
    }

    private void addWithRetry(String bid, BigDecimal value, AtomicInteger atomicInteger) {
        Exception e;
        do {
            e = null;
            try {
                ast.addOnce(bid, value);
            } catch (ObjectOptimisticLockingFailureException ex) {
                e = ex;
                atomicInteger.addAndGet(1);
            }
        } while (e != null);
    }


    public AccountDto setToAccount(String bid, BigDecimal value) {
        ast.setToAccount(bid, value);
        return getAccount(bid);
    }

    public AccountDto getAccount(String bid) {
        Optional<Account> byBid = repository.findByBid(bid);
        return byBid.map(accountMapper::map).orElseThrow(() -> new IllegalArgumentException("Account with bid " + bid + " does not exist"));
    }
}
