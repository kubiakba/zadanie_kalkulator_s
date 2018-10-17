package pl.bk.contract.domain.validator;

import pl.bk.contract.domain.exception.AppException;

import java.util.Locale;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static pl.bk.contract.domain.exception.ErrorCode.INVALID_COUNTRY_ISO_CODE;
import static pl.bk.contract.domain.exception.ErrorCode.INVALID_NUMBER_FORMAT;

public class ContractValidator
{
    public static void isCountryISOValid(String ISOCountry)
    {
        if(stream(Locale.getISOCountries())
            .noneMatch(country -> country.equals(ISOCountry.toUpperCase())))
        {
            throw new AppException(
                "Invalid iso code: " + ISOCountry + " have been passed.",
                BAD_REQUEST,
                INVALID_COUNTRY_ISO_CODE
            );
        }
    }
    
    public static void isEarningFormatValid(String earnings)
    {
        if(!earnings.matches("\\d+(\\.\\d+)?"))
        {
            throw new AppException(
                "Invalid earnings value: " + earnings + " have been passed.",
                BAD_REQUEST,
                INVALID_NUMBER_FORMAT
            );
        }
    }
    
}
