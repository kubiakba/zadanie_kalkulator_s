package pl.bk.contract.domain.tax.income;

import org.javamoney.moneta.Money;

public class GermanyDailyIncomeTax implements CountryDailyIncomeTax
{
    private final long taxPercent = 20;
    
    @Override
    public Money calculate(Money gross)
    {
        return gross.multiply(100 - taxPercent);
    }
    
    @Override
    public String getLocaleISOCountry()
    {
        return "DE";
    }
}
