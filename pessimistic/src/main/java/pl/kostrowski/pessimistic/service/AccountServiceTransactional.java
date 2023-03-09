package pl.kostrowski.pessimistic.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kostrowski.pessimistic.model.Account;
import pl.kostrowski.pessimistic.repository.AccountRepository;
import pl.kostrowski.pessimistic.util.SleepUtil;


import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceTransactional {

    private final AccountRepository accountRepository;

    @Transactional
    public void addOnceOptimistic(String bid, BigDecimal value) {
        Optional<Account> byBid = accountRepository.findByBid(bid);
        if (byBid.isPresent()) {
            byBid.get().setAmount(byBid.get().getAmount().add(value));
            SleepUtil.sleepFor(100);
        } else {
            throw new IllegalArgumentException("Bid " + bid + " does not exist");
        }
    }


    @Transactional
    public void addOncePessimisticRead(String bid, BigDecimal value) {
        Optional<Account> byBid = accountRepository.findByBidPessimisticRead(bid);
        if (byBid.isPresent()) {
            byBid.get().setAmount(byBid.get().getAmount().add(value));
            SleepUtil.sleepFor(100);
        } else {
            throw new IllegalArgumentException("Bid " + bid + " does not exist");
        }
    }

    @Transactional
    public void addOncePessimisticWrite(String bid, BigDecimal value) {
        Optional<Account> byBid = accountRepository.findByBidPessimisticWrite(bid);
        if (byBid.isPresent()) {
            byBid.get().setAmount(byBid.get().getAmount().add(value));
            SleepUtil.sleepFor(100);
        } else {
            throw new IllegalArgumentException("Bid " + bid + " does not exist");
        }
    }

    @Transactional
    public void addOncePessimisticWriteForceIncrement(String bid, BigDecimal value) {
        Optional<Account> byBid = accountRepository.findByBidPessimisticForce(bid);
        if (byBid.isPresent()) {
            byBid.get().setAmount(byBid.get().getAmount().add(value));
            SleepUtil.sleepFor(100);
        } else {
            throw new IllegalArgumentException("Bid " + bid + " does not exist");
        }
    }

    @Transactional
    public void setToAccount(String bid, BigDecimal value) {
        Optional<Account> byBid = accountRepository.findByBid(bid);
        if (byBid.isPresent()) {
            byBid.get().setAmount(value);
            accountRepository.save(byBid.get());
        } else {
            throw new IllegalArgumentException("Bid " + bid + " does not exist");
        }
    }
}
