package transfer.service;

import transfer.constant.Currency;

import java.io.IOException;
import java.math.BigDecimal;

public interface CurrencyService {

    BigDecimal getCurrencyRate(Currency from, Currency  to) throws IOException;
}
