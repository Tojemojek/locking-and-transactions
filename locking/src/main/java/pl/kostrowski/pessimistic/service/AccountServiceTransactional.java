package pl.kostrowski.pessimistic.service;

import static pl.kostrowski.pessimistic.PessimisticLockingApplication.SLEEP_TIME_MS;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kostrowski.pessimistic.model.audited.Account;
import pl.kostrowski.pessimistic.model.audited.AccountRepository;
import pl.kostrowski.pessimistic.model.notaudited.AccountNoVersion;
import pl.kostrowski.pessimistic.model.notaudited.AccountNoVersionRepository;
import pl.kostrowski.pessimistic.util.SleepUtil;

@Service
@RequiredArgsConstructor
public class AccountServiceTransactional {

  private final AccountRepository accountRepository;
  private final AccountNoVersionRepository accountNoVersionRepository;

  public AccountNoVersion addOnceNoLock(String bid, BigDecimal value) {
    Optional<AccountNoVersion> byBid = accountNoVersionRepository.findByBid(bid);
    if (byBid.isPresent()) {
      byBid.get()
           .setAmount(byBid.get()
                           .getAmount()
                           .add(value));
      SleepUtil.sleepFor(SLEEP_TIME_MS);
      return accountNoVersionRepository.save(byBid.get());
    } else {
      throw new IllegalArgumentException("Bid " + bid + " does not exist");
    }
  }

  public Account addOnceOptimistic(String bid, BigDecimal value) {
    Optional<Account> byBid = accountRepository.findByBid(bid);
    if (byBid.isPresent()) {
      byBid.get()
              .setAmount(byBid.get()
                      .getAmount()
                      .add(value));
      SleepUtil.sleepFor(SLEEP_TIME_MS);
      return accountRepository.save(byBid.get());
    } else {
      throw new IllegalArgumentException("Bid " + bid + " does not exist");
    }
  }

  @Transactional
  public Account addOncePessimisticRead(String bid, BigDecimal value) {
    Optional<Account> byBid = accountRepository.findByBidPessimisticRead(bid);
    if (byBid.isPresent()) {
      byBid.get()
           .setAmount(byBid.get()
                           .getAmount()
                           .add(value));
      SleepUtil.sleepFor(SLEEP_TIME_MS);
      return byBid.get();
    } else {
      throw new IllegalArgumentException("Bid " + bid + " does not exist");
    }
  }

  @Transactional
  public Account addOncePessimisticWrite(String bid, BigDecimal value) {
    Optional<Account> byBid = accountRepository.findByBidPessimisticWrite(bid);
    if (byBid.isPresent()) {
      byBid.get()
           .setAmount(byBid.get()
                           .getAmount()
                           .add(value));
      SleepUtil.sleepFor(SLEEP_TIME_MS);
      return byBid.get();
    } else {
      throw new IllegalArgumentException("Bid " + bid + " does not exist");
    }
  }

  @Transactional
  public Account addOncePessimisticWriteForceIncrement(String bid, BigDecimal value) {
    Optional<Account> byBid = accountRepository.findByBidPessimisticForce(bid);
    if (byBid.isPresent()) {
      byBid.get()
           .setAmount(byBid.get()
                           .getAmount()
                           .add(value));
      SleepUtil.sleepFor(SLEEP_TIME_MS);
      return byBid.get();
    } else {
      throw new IllegalArgumentException("Bid " + bid + " does not exist");
    }
  }

  @Transactional
  public Account setToAccount(String bid, BigDecimal value) {
    Optional<Account> byBid = accountRepository.findByBid(bid);
    if (byBid.isPresent()) {
      byBid.get()
           .setAmount(value);
      return accountRepository.save(byBid.get());
    } else {
      throw new IllegalArgumentException("Bid " + bid + " does not exist");
    }
  }

  @Transactional
  public AccountNoVersion setToAccountNoVersion(String bid, BigDecimal value) {
    Optional<AccountNoVersion> byBid = accountNoVersionRepository.findByBid(bid);
    if (byBid.isPresent()) {
      byBid.get()
           .setAmount(value);
      return accountNoVersionRepository.save(byBid.get());
    } else {
      throw new IllegalArgumentException("Bid " + bid + " does not exist");
    }
  }
}
