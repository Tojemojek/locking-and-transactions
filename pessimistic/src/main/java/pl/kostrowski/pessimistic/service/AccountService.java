package pl.kostrowski.pessimistic.service;

import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import pl.kostrowski.pessimistic.model.Account;
import pl.kostrowski.pessimistic.repository.AccountRepository;


import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountServiceTransactional ast;
    private final AccountRepository repository;

    public void addToAccount(String bid,
                             BigDecimal value,
                             boolean retry,
                             AtomicInteger atomicInteger,
                             LockModeType lockModeType) {
        if (retry) {
            addWithRetry(bid, value, atomicInteger, lockModeType);
        } else {
            useCorrectLockingMethod(bid, value, lockModeType);
        }
    }
    private void addWithRetry(String bid,
                              BigDecimal value,
                              AtomicInteger atomicInteger,
                              LockModeType lockModeType) {
        Exception e;
        do {
            e = null;
            try {
                useCorrectLockingMethod(bid, value, lockModeType);
            } catch (ObjectOptimisticLockingFailureException ex) {
                e = ex;
                atomicInteger.addAndGet(1);
            }
        } while (e != null);
    }

    private void useCorrectLockingMethod(String bid, BigDecimal value, LockModeType lockModeType) {
        switch (lockModeType) {
            case PESSIMISTIC_FORCE_INCREMENT -> ast.addOncePessimisticWriteForceIncrement(bid, value);
            case PESSIMISTIC_WRITE -> ast.addOncePessimisticWrite(bid, value);
            case PESSIMISTIC_READ -> ast.addOncePessimisticRead(bid, value);
            case OPTIMISTIC -> ast.addOnceOptimistic(bid, value);
            default -> throw new IllegalArgumentException("Lock mode type not supported");
        }
    }

    public Account setToAccount(String bid, BigDecimal value) {
        ast.setToAccount(bid, value);
        return getAccount(bid);
    }

    public Account getAccount(String bid) {
        Optional<Account> byBid = repository.findByBid(bid);
        return byBid.orElseThrow(() -> new IllegalArgumentException("Account with bid " + bid + " does not exist"));
    }
}
