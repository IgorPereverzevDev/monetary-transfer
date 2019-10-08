package transfer.service;

import org.jooby.Err;
import org.jooby.mvc.Body;
import org.modelmapper.ModelMapper;
import transfer.dto.AccountDTO;
import transfer.entities.Account;
import transfer.repository.AccountRepository;

import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final CurrencyService currencyService;
    private ModelMapper modelMapper;

    @Inject
    public AccountServiceImpl(AccountRepository accountRepository, CurrencyService currencyRatesService) {
        this.accountRepository = accountRepository;
        this.currencyService = currencyRatesService;
        modelMapper = new ModelMapper();
    }

    @Override
    public void transfer(UUID accountIdFrom, UUID accountIdTo, BigDecimal amount) throws IOException {
        Account accountFrom = accountRepository.findByAccountId(accountIdFrom).orElseThrow(() ->
                new IllegalStateException(String.format("Account sender with id %s not found",
                        accountIdFrom.toString())));

        Account accountTo = accountRepository.findByAccountId(accountIdTo).orElseThrow(() ->
                new IllegalStateException(String.format("Account receiver with id %s not found",
                        String.valueOf(accountIdTo))));

        BigDecimal rate = currencyService.getCurrencyRate(accountFrom.getCurrency(), accountTo.getCurrency());
        BigDecimal convertedAmountFrom = amount.multiply(rate);

        if (accountFrom.getBalance().compareTo(convertedAmountFrom) < 0) {
            throw new IllegalStateException(String.format("Account with accountId %s cannot send %s %s. Insufficient "
                    + "funds!", accountFrom.getAccountId(), convertedAmountFrom, accountTo.getCurrency()));
        }
        accountFrom.setBalance(accountFrom.getBalance().subtract(convertedAmountFrom));
        accountTo.setBalance(accountTo.getBalance().add(convertedAmountFrom));

        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.getAllAccounts()
                .map(account -> modelMapper.map(account, AccountDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccount(@Body UUID accountId) {
        return accountRepository.findByAccountId(accountId)
                .map(account -> modelMapper.map(account, AccountDTO.class))
                .orElseThrow(() -> new Err(404,
                        String.format("The following accountId: %s was not found", accountId.toString())));
    }

    @Override
    public Account addAccount(AccountDTO accountDTO) {
        return accountRepository.save(modelMapper.map(accountDTO, Account.class));
    }

    @Override
    public void deleteAccount(UUID accountId) {
        accountRepository.delete(accountId);
    }
}
