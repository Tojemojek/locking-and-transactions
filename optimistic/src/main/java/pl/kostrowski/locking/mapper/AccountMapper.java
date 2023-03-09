package pl.kostrowski.locking.mapper;

import org.mapstruct.Mapper;
import pl.kostrowski.locking.dto.AccountDto;
import pl.kostrowski.locking.model.Account;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    List<AccountDto> map(List<Account> accounts);
    AccountDto map(Account account);

}
