package pl.bk.contract.domain.tax.income;

import org.javamoney.moneta.Money;

public class UnitedKingdomDailyIncomeTax implements CountryDailyIncomeTax
{
    private final long taxPercent = 25;
    
    @Override
    public Money calculate(Money gross)
    {
        return gross.multiply(100 - taxPercent);
    }
    
    @Override
    public String getISOCountry()
    {
        return "UK";
    }
}
