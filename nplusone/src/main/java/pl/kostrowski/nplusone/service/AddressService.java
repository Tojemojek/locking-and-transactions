package pl.kostrowski.nplusone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kostrowski.nplusone.model.Address;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressServiceTransactional ast;

    public Address getByBidRead(String bid, int locktime) {
        return ast.getByBidRead(bid, locktime);
    }

    public Address getByBidWrite(String bid, int locktime) {
        return ast.getByBidWrite(bid, locktime);
    }

    public Address getByBidForce(String bid, int locktime) {
        return ast.getByBidForce(bid, locktime);
    }

    public Address getByBid(String bid, int locktime) {
        return ast.getByBid(bid, locktime);
    }

    public Address setStreetNo(String bid, Long streetNo, int locktime) {
        return ast.setStreetNo(bid, streetNo, locktime);
    }
}
