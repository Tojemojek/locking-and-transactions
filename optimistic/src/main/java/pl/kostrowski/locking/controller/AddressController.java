package pl.kostrowski.locking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kostrowski.locking.dto.AddressDto;
import pl.kostrowski.locking.service.AddressService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AddressController {

    private final AddressService addressService;

    @GetMapping(path = "/address/get/no/{bid}")
    public AddressDto getNoLock(@PathVariable("bid") String bid,
                              @RequestParam(name = "locktime", required = false, defaultValue = "0") int locktime) {
        log.debug("Getting address with bid {}", bid);
        return addressService.getByBid(bid, locktime);
    }

    @GetMapping(path = "/address/get/read/{bid}")
    public AddressDto getRead(@PathVariable("bid") String bid,
                              @RequestParam(name = "locktime", required = false, defaultValue = "0") int locktime) {
        log.debug("Getting address with bid {}", bid);
        return addressService.getByBidRead(bid, locktime);
    }

    @GetMapping(path = "/address/get/write/{bid}")
    public AddressDto getWrite(@PathVariable("bid") String bid,
                               @RequestParam(name = "locktime", required = false, defaultValue = "0") int locktime) {
        log.debug("Getting address with bid {}", bid);
        return addressService.getByBidWrite(bid, locktime);
    }

    @GetMapping(path = "/address/get/force/{bid}")
    public AddressDto getForce(@PathVariable("bid") String bid,
                               @RequestParam(name = "locktime", required = false, defaultValue = "0") int locktime) {
        log.debug("Getting address with bid {}", bid);
        return addressService.getByBidForce(bid, locktime);
    }


    @GetMapping(path = "/address/save/{bid}/{streetNo}")
    public AddressDto getForce(@PathVariable("bid") String bid,
                               @PathVariable("streetNo") Long streetNo,
                               @RequestParam(name = "locktime", required = false, defaultValue = "0") int locktime) {
        log.debug("Getting address with bid {}", bid);
        return addressService.setStreetNo(bid, streetNo, locktime);
    }
}
