package pl.bk.contract.domain.tax.fixed;

import lombok.Getter;
import org.javamoney.moneta.Money;

import static javax.money.Monetary.getCurrency;
import static org.javamoney.moneta.Money.*;

@Getter
public enum FixedCostsTax
{
    GB(of(600, getCurrency("GBP"))),
    DE(of(800, getCurrency("EUR"))),
    PL(of(1200, getCurrency("PLN")));
    
    private final Money money;
    
    FixedCostsTax(Money money)
    {
        this.money = money;
    }
}
