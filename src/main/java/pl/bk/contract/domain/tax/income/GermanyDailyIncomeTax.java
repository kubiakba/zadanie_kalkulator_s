package pl.bk.contract.domain.tax.income;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

@Service
public class GermanyDailyIncomeTax implements CountryDailyIncomeTax
{
    private final static long TAX_PERCENT = 20;
    private final static String COUNTRY_ISO_CODE = "DE";
    
    @Override
    public Money calculate(Money gross)
    {
        return gross.multiply(100 - TAX_PERCENT).divide(100);
    }
    
    @Override
    public String getISOCountry()
    {
        return COUNTRY_ISO_CODE;
    }
}
