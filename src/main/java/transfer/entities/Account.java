package transfer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import transfer.constant.Currency;

import java.math.BigDecimal;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private UUID accountId;
    private Currency currency;
    private volatile BigDecimal balance;
}
