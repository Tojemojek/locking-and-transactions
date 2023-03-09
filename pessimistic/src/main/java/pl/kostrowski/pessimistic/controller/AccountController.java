package pl.kostrowski.pessimistic.controller;

import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kostrowski.pessimistic.model.Account;
import pl.kostrowski.pessimistic.service.AccountService;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @GetMapping(path = "/account/add/optimistic")
    public Integer addToAccountOptimistic(@RequestParam("bid") String bid,
                                          @RequestParam("value") BigDecimal value,
                                          @RequestParam(name = "retry", required = false,
                                                  defaultValue = "false") boolean retry) {
        AtomicInteger retryCount = new AtomicInteger(0);
        accountService.addToAccount(bid, value, retry, retryCount, LockModeType.OPTIMISTIC);
        log.debug("Adding to account with bid {}, value {}", bid, value);
        log.warn("\n\n Retry count {}", retryCount.get());
        return retryCount.get();
    }

    @GetMapping(path = "/account/add/pesimistic/read")
    public Integer addToAccountPessimisticRead(@RequestParam("bid") String bid,
                                               @RequestParam("value") BigDecimal value,
                                               @RequestParam(name = "retry", required = false,
                                                       defaultValue = "false") boolean retry) {
        AtomicInteger retryCount = new AtomicInteger(0);
        accountService.addToAccount(bid, value, retry, retryCount, LockModeType.PESSIMISTIC_READ);
        log.debug("Adding to account with bid {}, value {}", bid, value);
        log.warn("\n\n Retry count {}", retryCount.get());
        return retryCount.get();
    }

    @GetMapping(path = "/account/add/pesimistic/write")
    public Integer addToAccountPessimisticWrite(@RequestParam("bid") String bid,
                                                @RequestParam("value") BigDecimal value,
                                                @RequestParam(name = "retry", required = false,
                                                        defaultValue = "false") boolean retry) {
        AtomicInteger retryCount = new AtomicInteger(0);
        accountService.addToAccount(bid, value, retry, retryCount, LockModeType.PESSIMISTIC_WRITE);
        log.debug("Adding to account with bid {}, value {}", bid, value);
        log.warn("\n\n Retry count {}", retryCount.get());
        return retryCount.get();
    }

    @GetMapping(path = "/account/add/pesimistic/write-force")
    public Integer addToAccountPessimisticWriteForce(@RequestParam("bid") String bid,
                                                     @RequestParam("value") BigDecimal value,
                                                     @RequestParam(name = "retry", required =
                                                             false, defaultValue = "false") boolean retry) {
        AtomicInteger retryCount = new AtomicInteger(0);
        accountService.addToAccount(bid,
                                    value,
                                    retry,
                                    retryCount,
                                    LockModeType.PESSIMISTIC_FORCE_INCREMENT);
        log.debug("Adding to account with bid {}, value {}", bid, value);
        log.warn("\n\n Retry count {}", retryCount.get());
        return retryCount.get();
    }


    @GetMapping(path = "/account/set")
    public Account setToAccount(@RequestParam("bid") String bid,
                                @RequestParam("value") BigDecimal value) {
        log.debug("Setting to account with bid {}, value {}", bid, value);
        return accountService.setToAccount(bid, value);
    }

    @GetMapping(path = "/account/get")
    public Account getAccount(@RequestParam("bid") String bid) {
        log.debug("Getting account with bid {}", bid);
        return accountService.getAccount(bid);
    }

}
