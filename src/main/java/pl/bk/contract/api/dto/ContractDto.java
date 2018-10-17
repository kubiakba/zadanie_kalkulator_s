package pl.bk.contract.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ContractDto
{
    private String countryISO;
    private String dailyGrossEarnings;
}
