package pl.bk.contract.application;

import lombok.AllArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import pl.bk.contract.api.dto.ContractDto;
import pl.bk.contract.domain.calculator.ContractCalculator;
import pl.bk.contract.domain.exchange.CurrencyUnitExchanger;

import javax.money.Monetary;

@Service
@AllArgsConstructor
public class ContractService
{
    private final ContractCalculator contractCalculator;
    private final CurrencyUnitExchanger currencyUnitExchanger;
    
    public ContractDto calculateDailyNetEarningsInPLN(ContractDto contractDto)
    {
        final Money dailyNetEarnings = contractCalculator.calculateDailyNetEarnings(contractDto);
        final Money money = currencyUnitExchanger.convertMoneyToPLN(dailyNetEarnings).with(Monetary.getDefaultRounding());
        return new ContractDto("PL", money.getNumber().toString());
    }
}
