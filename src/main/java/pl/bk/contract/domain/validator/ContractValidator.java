package pl.bk.contract.domain.validator;

import pl.bk.contract.domain.exception.AppException;

import java.util.Locale;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static pl.bk.contract.domain.exception.ErrorCode.INVALID_COUNTRY_ISO_CODE;

public class ContractValidator
{
    public static void isCountryISOValid(String ISOCountry)
    {
        if(stream(Locale.getAvailableLocales())
            .noneMatch(locale -> locale.getCountry().toLowerCase()
                                       .contains(ISOCountry.toLowerCase())))
        {
            throw new AppException(
                "Invalid iso code: " + ISOCountry + " have been passed.",
                BAD_REQUEST,
                INVALID_COUNTRY_ISO_CODE
            );
        }
    }
}
