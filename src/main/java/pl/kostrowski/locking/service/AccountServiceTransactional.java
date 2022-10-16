package pl.kostrowski.locking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kostrowski.locking.model.Account;
import pl.kostrowski.locking.repository.AccountRepository;
import pl.kostrowski.locking.util.SleepUtil;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceTransactional {

    private final AccountRepository accountRepository;

    @Transactional
    public void addOnce(String bid, BigDecimal value) {
        Optional<Account> byBid = accountRepository.findByBid(bid);
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
