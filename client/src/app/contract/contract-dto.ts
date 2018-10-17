export class ContractDto {

  countryISO: String;
  dailyGrossEarnings: String;

  constructor(countryISO: String, dailyGrossEarnings: String) {
    this.countryISO = countryISO;
    this.dailyGrossEarnings = dailyGrossEarnings;
  }
}
