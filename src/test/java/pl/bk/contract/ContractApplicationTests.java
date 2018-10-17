package pl.bk.contract;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.bk.contract.api.dto.ContractDto;
import pl.bk.contract.domain.exception.ErrorMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.bk.contract.domain.exception.ErrorCode.INVALID_COUNTRY_ISO_CODE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ContractApplicationTests
{
    TestRestTemplate testRestTemplate = new TestRestTemplate();
    
    @Test
    public void shouldReturn200WhenCallingAPI()
    {
        //when
        final ResponseEntity<ContractDto> response = getContractApiResponse(new ContractDto("GB", "200"), ContractDto.class);
        //then
        assertThat(response.getBody().getCountryISO()).isEqualTo("PL");
        assertThat(response.getBody().getDailyGrossEarnings()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    @Test
    public void shouldReturn400WhenCallingAPIWithInvalidCountryISO()
    {
        //given
        final String url = "http://localhost:8080/contract";
        final ContractDto contract = new ContractDto("PL3", "200");
        //when
        ResponseEntity<ErrorMessage> error = testRestTemplate.postForEntity(url, contract, ErrorMessage.class);
        //then
        assertThat(error.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(error.getBody().getErrorCode()).isEqualTo(INVALID_COUNTRY_ISO_CODE);
    }
    
    private <T> ResponseEntity<T> getContractApiResponse(ContractDto contract, Class<T> clazz)
    {
        return testRestTemplate.postForEntity("http://localhost:8080/contract", contract, clazz);
    }
}
