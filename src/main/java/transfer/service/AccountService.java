package transfer.service;

import org.jooby.mvc.Body;
import transfer.dto.AccountDTO;
import transfer.entities.Account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    void transfer(UUID accountIdFrom, UUID accountIdTo, BigDecimal amount) throws IOException;

    List<AccountDTO> getAllAccounts();

    AccountDTO getAccount(UUID accountId);

    Account addAccount(@Body AccountDTO accountDTO);

    void deleteAccount(UUID accountId);
}
