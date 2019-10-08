package transfer.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import transfer.constant.Currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CurrencyServiceImpl implements CurrencyService {

    private Map<Rate, BigDecimal> availableRates =
            new HashMap<Rate,BigDecimal>(){{
                put(new Rate(Currency.EURO, Currency.GREAT_BRITAIN_POUND), new BigDecimal("0.89"));
                put(new Rate(Currency.EURO, Currency.POLAND_ZLOTY), new BigDecimal("4.26"));
                put(new Rate(Currency.USA_DOLLAR, Currency.GREAT_BRITAIN_POUND), new BigDecimal("0.78"));
                put(new Rate(Currency.USA_DOLLAR, Currency.POLAND_ZLOTY), new BigDecimal("3.76"));
            }};

    @AllArgsConstructor
    @Data
    private static class Rate {
        private Currency currencyFrom;
        private Currency currencyTo;
    }

    @Override
    public BigDecimal getCurrencyRate(Currency from, Currency to) {
        return from.equals(to) ? BigDecimal.ONE :
                Optional.ofNullable(availableRates.get(new Rate(from, to)))
                        .orElseThrow(() ->
                                new IllegalStateException(String.format("No rate found for conversion %s to %s",
                                        from.name(), to.name())));
    }
}
