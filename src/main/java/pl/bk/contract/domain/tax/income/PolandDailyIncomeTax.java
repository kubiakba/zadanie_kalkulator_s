package pl.bk.contract.domain.tax.income;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

@Service
public class PolandDailyIncomeTax implements CountryDailyIncomeTax
{
    private final long taxPercent = 19;
    
    @Override
    public Money calculate(Money gross)
    {
        return gross.multiply(100 - taxPercent);
    }
    
    @Override
    public String getISOCountry()
    {
        return "PL";
    }
}
