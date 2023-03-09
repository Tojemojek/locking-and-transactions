package pl.kostrowski.locking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kostrowski.locking.dto.AddressDto;
import pl.kostrowski.locking.mapper.AddressMapper;
import pl.kostrowski.locking.model.Address;
import pl.kostrowski.locking.repository.AddressRepository;
import pl.kostrowski.locking.util.SleepUtil;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceTransactional {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public AddressDto getByBidRead(String bid, int locktime) {
        Optional<Address> byBid = addressRepository.findByBidRead(bid);
        SleepUtil.sleepFor(locktime);
        return byBid.map(addressMapper::map).orElseThrow(() -> new IllegalArgumentException("Address with bid " + bid + " does not exist"));
    }
    @Transactional
    public AddressDto getByBidWrite(String bid, int locktime) {
        Optional<Address> byBid = addressRepository.findByBidWrite(bid);
        SleepUtil.sleepFor(locktime);
        return byBid.map(addressMapper::map).orElseThrow(() -> new IllegalArgumentException("Address with bid " + bid + " does not exist"));
    }

    @Transactional
    public AddressDto getByBidForce(String bid, int locktime) {
        Optional<Address> byBid = addressRepository.findByBidForceIncrement(bid);
        SleepUtil.sleepFor(locktime);
        return byBid.map(addressMapper::map).orElseThrow(() -> new IllegalArgumentException("Address with bid " + bid + " does not exist"));
    }

    @Transactional
    public AddressDto getByBid(String bid, int locktime) {
        Optional<Address> byBid = addressRepository.findByBid(bid);
        SleepUtil.sleepFor(locktime);
        return byBid.map(addressMapper::map).orElseThrow(() -> new IllegalArgumentException("Address with bid " + bid + " does not exist"));
    }

    @Transactional
    public AddressDto setStreetNo(String bid, Long streetNo, int locktime) {
        Optional<Address> byBid = addressRepository.findByBid(bid);
        SleepUtil.sleepFor(locktime);
        if (byBid.isPresent()) {
            byBid.get().setStreetNo(streetNo);
        }
        return byBid.map(addressMapper::map).orElseThrow(() -> new IllegalArgumentException("Address with bid " + bid + " does not exist"));
    }
}
