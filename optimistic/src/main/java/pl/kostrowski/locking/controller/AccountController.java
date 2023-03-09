package pl.kostrowski.locking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kostrowski.locking.dto.AccountDto;
import pl.kostrowski.locking.service.AccountService;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping(path = "/account/add/{bid}/{value}")
    public void addToAccount(@PathVariable("bid") String bid, @PathVariable("value") BigDecimal value,
                             @RequestParam(name = "retry", required = false, defaultValue = "false") boolean retry) {
        AtomicInteger retryCount = new AtomicInteger(0);
        accountService.addToAccount(bid, value, retry, retryCount);
        log.debug("Adding to account with bid {}, value {}", bid, value);
        log.warn("\n\n Retry count {}", retryCount.get());
    }

    @GetMapping(path = "/account/set/{bid}/{value}")
    public AccountDto setToAccount(@PathVariable("bid") String bid, @PathVariable("value") BigDecimal value) {
        log.debug("Setting to account with bid {}, value {}", bid, value);
        return accountService.setToAccount(bid, value);
    }

    @GetMapping(path = "/account/get/{bid}")
    public AccountDto getAccount(@PathVariable("bid") String bid) {
        log.debug("Getting account with bid {}", bid);
        return accountService.getAccount(bid);
    }

}
