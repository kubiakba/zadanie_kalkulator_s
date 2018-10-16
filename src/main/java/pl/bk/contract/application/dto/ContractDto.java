package pl.bk.contract.application.dto;

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
