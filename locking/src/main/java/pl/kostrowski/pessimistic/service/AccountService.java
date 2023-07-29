package pl.kostrowski.pessimistic.service;

import jakarta.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import pl.kostrowski.pessimistic.controller.OperationResult;
import pl.kostrowski.pessimistic.model.audited.Account;
import pl.kostrowski.pessimistic.model.audited.AccountRepository;
import pl.kostrowski.pessimistic.model.notaudited.AccountNoVersion;
import pl.kostrowski.pessimistic.model.notaudited.AccountNoVersionRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountServiceTransactional ast;
  private final AccountRepository repository;
  private final AccountNoVersionRepository repositoryNoVersion;

  public AccountNoVersion addToNoLockAccount(String bid, BigDecimal value) {
    return ast.addOnceNoLock(bid, value);
  }

  public OperationResult addToAccount(String bid,
                                      BigDecimal value,
                                      boolean retry,
                                      AtomicInteger counter,
                                      LockModeType lockMode) {
    if (retry) {
      return addWithRetry(bid, value, counter, lockMode);
    } else {
      Account account = addWithLockingMode(bid, value, lockMode);
      return new OperationResult(account, 0);
    }
  }

  private OperationResult addWithRetry(String bid,
                                       BigDecimal value,
                                       AtomicInteger atomicInteger,
                                       LockModeType lockModeType) {
    Exception e;
    Account modified = null;
    do {
      e = null;
      try {
        modified = addWithLockingMode(bid, value, lockModeType);
      } catch (ObjectOptimisticLockingFailureException ex) {
        e = ex;
        atomicInteger.addAndGet(1);
      }
    } while (e != null);
    return new OperationResult(modified, atomicInteger.get());
  }

  private Account addWithLockingMode(String bid, BigDecimal value, LockModeType lockModeType) {
    return switch (lockModeType) {
      case PESSIMISTIC_FORCE_INCREMENT -> ast.addOncePessimisticWriteForceIncrement(bid, value);
      case PESSIMISTIC_WRITE -> ast.addOncePessimisticWrite(bid, value);
      case PESSIMISTIC_READ -> ast.addOncePessimisticRead(bid, value);
      case OPTIMISTIC -> ast.addOnceOptimistic(bid, value);
      default -> throw new IllegalArgumentException("Lock mode type not supported");
    };
  }

  public Account setToAccount(String bid, BigDecimal value) {
    ast.setToAccount(bid, value);
    return getAccount(bid);
  }

  public Account getAccount(String bid) {
    Optional<Account> byBid = repository.findByBid(bid);
    return byBid.orElseThrow(
        () -> new IllegalArgumentException("Account with bid " + bid + " does not exist"));
  }

  public AccountNoVersion setToAccountNoVersion(String bid, BigDecimal value) {
    ast.setToAccountNoVersion(bid, value);
    return getAccountNoVersion(bid);
  }

  public AccountNoVersion getAccountNoVersion(String bid) {
    Optional<AccountNoVersion> byBid = repositoryNoVersion.findByBid(bid);
    return byBid.orElseThrow(
        () -> new IllegalArgumentException("Account with bid " + bid + " does not exist"));
  }
}
