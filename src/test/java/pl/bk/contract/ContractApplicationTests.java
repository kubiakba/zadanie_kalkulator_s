package pl.bk.contract;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import pl.bk.contract.api.dto.ContractDto;
import pl.bk.contract.domain.exception.ErrorMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.OK;
import static pl.bk.contract.domain.exception.ErrorCode.COUNTRY_NOT_SUPPORTED;
import static pl.bk.contract.domain.exception.ErrorCode.INVALID_COUNTRY_ISO_CODE;
import static pl.bk.contract.domain.exception.ErrorCode.INVALID_NUMBER_FORMAT;

@RunWith(JUnitParamsRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ContractApplicationTests
{
    @Autowired
    TestRestTemplate testRestTemplate;
    
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();
    
    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();
    
    @Test
    @Parameters({"PL", "GB", "DE"})
    public void shouldReturn200WhenCallingAPIWithValidCountryISO(String countryISO)
    {
        //when
        final ResponseEntity<ContractDto> response = getContractApiResponse(new ContractDto(countryISO, "200"), ContractDto.class);
        //then
        assertThat(response.getBody().getCountryISO()).isEqualTo("PL");
        assertThat(response.getBody().getDailyGrossEarnings()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }
    
    @Test
    @Parameters({"10", "23.9", "200.00", "49", "400", "600.40", "10000.00","30.9984"})
    public void shouldReturn200WhenCallingAPIWithValidEarnings(String earnings)
    {
        //when
        final ResponseEntity<ContractDto> response = getContractApiResponse(new ContractDto("PL", earnings), ContractDto.class);
        //then
        assertThat(response.getBody().getCountryISO()).isEqualTo("PL");
        assertThat(response.getBody().getDailyGrossEarnings()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }
    
    @Test
    @Parameters({"AU", "BO", "CI"})
    public void shouldThrowErrorWhenCallingAPIWithNotSupportedCountries(String countryISO)
    {
        //when
        final ResponseEntity<ErrorMessage> error = getContractApiResponse(new ContractDto(countryISO, "200"), ErrorMessage.class);
        //then
        assertThat(error.getStatusCode()).isEqualTo(NOT_ACCEPTABLE);
        assertThat(error.getBody().getErrorCode()).isEqualTo(COUNTRY_NOT_SUPPORTED);
    }
    
    @Test
    @Parameters({"null", "iv23", "ii", "-", "hny", "", "   "})
    public void shouldThrowErrorWhenCallingAPIWithInvalidCountryISO(String countryISO)
    {
        //when
        ResponseEntity<ErrorMessage> error = getContractApiResponse(new ContractDto(countryISO, "299.23"), ErrorMessage.class);
        //then
        assertThat(error.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(error.getBody().getErrorCode()).isEqualTo(INVALID_COUNTRY_ISO_CODE);
    }
    
    @Test
    @Parameters({"-93.98", "-40", "0.0034.03", "-.09.92-", "3f", "099.271c", "  ", "null", ""})
    public void shouldThrowErrorWhenCallingAPIWithInvalidEarnings(String earnings)
    {
        //when
        ResponseEntity<ErrorMessage> error = getContractApiResponse(new ContractDto("PL", earnings), ErrorMessage.class);
        //then
        assertThat(error.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(error.getBody().getErrorCode()).isEqualTo(INVALID_NUMBER_FORMAT);
    }
    
    private <T> ResponseEntity<T> getContractApiResponse(ContractDto contract, Class<T> clazz)
    {
        return testRestTemplate.postForEntity("http://localhost:8080/contract", contract, clazz);
    }
}
