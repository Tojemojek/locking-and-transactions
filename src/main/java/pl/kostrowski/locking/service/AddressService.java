package pl.kostrowski.locking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kostrowski.locking.dto.AddressDto;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressServiceTransactional ast;

    public AddressDto getByBidRead(String bid, int locktime) {
        return ast.getByBidRead(bid, locktime);
    }

    public AddressDto getByBidWrite(String bid, int locktime) {
        return ast.getByBidWrite(bid, locktime);
    }

    public AddressDto getByBidForce(String bid, int locktime) {
        return ast.getByBidForce(bid, locktime);
    }

    public AddressDto getByBid(String bid, int locktime) {
        return ast.getByBid(bid, locktime);
    }

    public AddressDto setStreetNo(String bid, Long streetNo, int locktime) {
        return ast.setStreetNo(bid, streetNo, locktime);
    }
}
