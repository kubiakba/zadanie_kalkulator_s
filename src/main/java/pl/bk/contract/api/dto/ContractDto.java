package pl.bk.contract.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ContractDto
{
    private String ISOCountry;
    private String dailyGrossEarnings;
}
