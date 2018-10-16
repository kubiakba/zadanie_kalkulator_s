package pl.bk.contract.domain.validator;

import pl.bk.contract.api.dto.ContractDto;
import pl.bk.contract.domain.exception.AppException;
import pl.bk.contract.domain.exception.ErrorCode;
import pl.bk.contract.domain.tax.income.CountryDailyIncomeTax;

import java.util.Set;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

public class DailyIncomeTaxValidator
{
    public static void isCalculationForCountrySupported(Set<CountryDailyIncomeTax> countriesDailyIncomeTax, ContractDto contract)
    {
        final long countries = countriesDailyIncomeTax
            .stream()
            .filter(country -> country.getISOCountry().equals(contract.getISOCountry()))
            .count();
        
        if(countries == 0)
        {
            throw new AppException(
                "Country with iso: " + contract.getISOCountry() + " is not supported.",
                NOT_ACCEPTABLE,
                ErrorCode.COUNTRY_NOT_SUPPORTED
            );
        }
        if(countries > 1)
        {
            throw new AppException(
                "Country with iso: " + contract.getISOCountry() + " have more than one implementation.",
                INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR
            );
        }
    }
}
