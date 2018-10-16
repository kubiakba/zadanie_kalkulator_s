package pl.bk.contract.domain.validator;

import pl.bk.contract.domain.exception.AppException;
import pl.bk.contract.domain.exception.ErrorCode;
import pl.bk.contract.domain.tax.fixed.FixedCostsTax;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

public class FixedCostsTaxValidator
{
    public static void isFixedCostsDefinedForCountry(String ISOCountry)
    {
        if(stream(FixedCostsTax.values()).noneMatch(country -> country.name().equals(ISOCountry)))
        {
            throw new AppException(
                "Country with iso: " + ISOCountry + " is not supported.",
                NOT_ACCEPTABLE,
                ErrorCode.COUNTRY_NOT_SUPPORTED
            );
        }
    }
}
