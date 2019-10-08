package trasfer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import transfer.constant.Currency;
import transfer.entities.Account;
import transfer.repository.AccountRepository;
import transfer.service.AccountService;
import transfer.service.AccountServiceImpl;
import transfer.service.CurrencyService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class MoneyTransferUnitTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CurrencyService currencyRatesService;

    private AccountService accountService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        accountService = new AccountServiceImpl(accountRepository, currencyRatesService);
    }

    @Test
    public void makeTransaction() throws IOException {

        UUID accountIDSender = UUID.randomUUID();
        UUID accountIdDestination = UUID.randomUUID();

        Account accountSender = new Account(accountIDSender, Currency.EURO, new BigDecimal("1400"));
        Account accountDestination = new Account(accountIdDestination, Currency.GREAT_BRITAIN_POUND, new BigDecimal("1000"));

        Mockito.when(accountRepository.findByAccountId(accountIDSender))
                .thenReturn(Optional.of(accountSender));
        Mockito.when(accountRepository.findByAccountId(accountIdDestination))
                .thenReturn(Optional.of(accountDestination));
        Mockito.when(currencyRatesService.getCurrencyRate(Currency.EURO, Currency.GREAT_BRITAIN_POUND))
                .thenReturn(new BigDecimal("0.89"));

        accountService.transfer(accountIDSender, accountIdDestination, new BigDecimal("10"));

        assertEquals(0, accountSender.getBalance().compareTo(BigDecimal.valueOf(1391.1)));
        assertEquals("1008.90", accountDestination.getBalance().toString());

    }
}
