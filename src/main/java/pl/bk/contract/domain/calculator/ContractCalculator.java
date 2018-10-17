package pl.bk.contract.domain.calculator;

import lombok.AllArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import pl.bk.contract.api.dto.ContractDto;
import pl.bk.contract.domain.tax.fixed.FixedCostsTax;
import pl.bk.contract.domain.tax.income.CountryDailyIncomeTax;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.Currency.getInstance;
import static java.util.Locale.*;
import static java.util.stream.Stream.of;
import static javax.money.Monetary.getCurrency;
import static pl.bk.contract.domain.validator.ContractValidator.isCountryISOValid;
import static pl.bk.contract.domain.validator.DailyIncomeTaxValidator.isCalculationForCountrySupported;
import static pl.bk.contract.domain.validator.FixedCostsTaxValidator.isFixedCostsDefinedForCountry;

@Service
@AllArgsConstructor
public class ContractCalculator
{
    private final Set<CountryDailyIncomeTax> countriesDailyIncomeTax;
    private final long NUMBER_OF_DAYS_IN_MONTH = 22;
    
    public Money calculateDailyNetEarnings(ContractDto contract)
    {
        isCountryISOValid(contract.getCountryISO());
        final CurrencyUnit unit = getCurrencyUnit(contract);
        
        final Money dailyGrossEarnings = Money.of(new BigDecimal(contract.getDailyGrossEarnings()), unit);
        
        isCalculationForCountrySupported(countriesDailyIncomeTax, contract);
        
        final Money dailyGrossMinusIncomeTax = applyDailyIncomeTax(contract, dailyGrossEarnings);
        
        final Money monthEarnings = calculateMonthEarnings(dailyGrossMinusIncomeTax);
        
        final Money monthEarningMinusFixCosts = subtractFixCosts(contract, monthEarnings);
        
        return calculateDailyNetEarnings(monthEarningMinusFixCosts);
    }
    
    private Money calculateDailyNetEarnings(Money monthEarningMinusFixCosts)
    {
        return monthEarningMinusFixCosts.divide(NUMBER_OF_DAYS_IN_MONTH);
    }
    
    private Money subtractFixCosts(ContractDto contract, Money monthEarnings)
    {
        final FixedCostsTax costs = getFixCostsForCountry(contract.getCountryISO());
        return monthEarnings.subtract(costs.getMoney());
    }
    
    private FixedCostsTax getFixCostsForCountry(String isoCountry)
    {
        isFixedCostsDefinedForCountry(isoCountry);
        return stream(FixedCostsTax.values())
            .filter(it -> isoCountry.equals(it.name()))
            .findFirst()
            .get();
    }
    
    private Money calculateMonthEarnings(Money moneyMinusIncomeTax)
    {
        return moneyMinusIncomeTax.multiply(NUMBER_OF_DAYS_IN_MONTH);
    }
    
    private Money applyDailyIncomeTax(ContractDto contract, Money dailyEarnings)
    {
        return of(dailyEarnings)
            .map(money ->
                     countriesDailyIncomeTax
                         .stream()
                         .filter(country -> country.getISOCountry().equals(contract.getCountryISO()))
                         .findFirst()
                         .get()
                         .calculate(money))
            .findFirst()
            .get();
    }
    
    private CurrencyUnit getCurrencyUnit(ContractDto contract)
    {
        final Currency currency = getCurrencyFrom(contract);
        return getCurrency(currency.getCurrencyCode());
    }
    
    private Currency getCurrencyFrom(ContractDto contract)
    {
        isCountryISOValid(contract.getCountryISO());
        final Locale locale = Arrays.stream(getAvailableLocales())
                                    .filter(loc -> loc.getCountry().toLowerCase()
                                                      .contains(contract.getCountryISO().toLowerCase()))
                                    .findFirst()
                                    .get();
        return getInstance(locale);
    }
    
}
