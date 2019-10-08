package transfer.repository;

import com.google.inject.Singleton;
import transfer.entities.Account;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Singleton
public class AccountRepositoryImpl implements AccountRepository {

    private Map<UUID, Account> accountsPersistence = new ConcurrentHashMap<>();

    @Override
    public Stream<Account> getAllAccounts() {
        return accountsPersistence.values().stream();
    }

    @Override
    public Optional<Account> findByAccountId(UUID accountId) {
        return Optional.ofNullable(accountsPersistence.get(accountId));
    }

    @Override
    public Account save(Account account) {
        accountsPersistence.put(account.getAccountId(), account);
        return account;
    }

    @Override
    public void delete(UUID accountId) {
        accountsPersistence.remove(accountId);
    }
}
