package pl.bk.contract.api.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.bk.contract.api.dto.ContractDto;
import pl.bk.contract.application.ContractService;

@RestController
@AllArgsConstructor
public class ContractEndpoint
{
    private final ContractService contractService;
    
    @PostMapping("/contract")
    public ResponseEntity<ContractDto> calculateDailyNetGrossInPLN(@RequestBody ContractDto contractDto)
    {
        return ResponseEntity.ok(contractService.calculateDailyNetEarningsInPLN(contractDto));
    }
    
}
