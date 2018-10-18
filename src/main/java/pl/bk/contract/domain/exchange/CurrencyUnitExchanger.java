package pl.bk.contract.domain.exchange;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;

@Service
public class CurrencyUnitExchanger
{
    public Money convertMoneyToPLN(Money money)
    {
        final CurrencyConversion toPLNConversion = MonetaryConversions.getConversion("PLN");
        return money.with(toPLNConversion);
    }
}

