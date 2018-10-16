package pl.bk.contract.domain.tax.income;

import org.javamoney.moneta.Money;

public interface CountryDailyIncomeTax
{
    Money calculate(Money gross);
    
    String getLocaleISOCountry();
}
