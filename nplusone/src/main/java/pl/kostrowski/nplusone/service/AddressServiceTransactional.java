package pl.kostrowski.nplusone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kostrowski.nplusone.model.Address;
import pl.kostrowski.nplusone.repository.AddressRepository;
import pl.kostrowski.nplusone.util.SleepUtil;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceTransactional {

  private final AddressRepository addressRepository;

  @Transactional
  public Address getByBidRead(String bid, int locktime) {
    Optional<Address> byBid = addressRepository.findByBidRead(bid);
    SleepUtil.sleepFor(locktime);
    return byBid.orElseThrow(
        () -> new IllegalArgumentException("Address with bid " + bid + " does not exist"));
  }

  @Transactional
  public Address getByBidWrite(String bid, int locktime) {
    Optional<Address> byBid = addressRepository.findByBidWrite(bid);
    SleepUtil.sleepFor(locktime);
    return byBid.orElseThrow(
        () -> new IllegalArgumentException("Address with bid " + bid + " does not exist"));
  }

  @Transactional
  public Address getByBidForce(String bid, int locktime) {
    Optional<Address> byBid = addressRepository.findByBidForceIncrement(bid);
    SleepUtil.sleepFor(locktime);
    return byBid.orElseThrow(
        () -> new IllegalArgumentException("Address with bid " + bid + " does not exist"));
  }

  @Transactional
  public Address getByBid(String bid, int locktime) {
    Optional<Address> byBid = addressRepository.findByBid(bid);
    SleepUtil.sleepFor(locktime);
    return byBid.orElseThrow(
        () -> new IllegalArgumentException("Address with bid " + bid + " does not exist"));
  }

  @Transactional
  public Address setStreetNo(String bid, Long streetNo, int locktime) {
    Optional<Address> byBid = addressRepository.findByBid(bid);
    SleepUtil.sleepFor(locktime);
    if (byBid.isPresent()) {
      byBid.get()
           .setStreetNo(streetNo);
    }
    return byBid.orElseThrow(
        () -> new IllegalArgumentException("Address with bid " + bid + " does not exist"));
  }
}
