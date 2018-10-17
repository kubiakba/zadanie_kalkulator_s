import {Component} from '@angular/core';
import {ContractService} from "./contract.service";
import {ContractDto} from "./contract-dto";

@Component({
  selector: 'app-contract',
  template: `
    <br/>
    <form>
      <div class="form-row">
        <div class="form-group col-sm-2">
          <input [(ngModel)]="salary" name="salary" type="text" class="form-control" id="inputZip" placeholder="gross daily earnings">
        </div>
        <div class="form-group col-md-1">
          <select [(ngModel)]="country" name="country" id="inputState" class="form-control">
            <option selected>PL</option>
            <option>DE</option>
            <option>GB</option>
          </select>
        </div>
        <div class="form-group col-sm-1">
          <button type="submit" (click)="calculateDailyNetEarningsInPLN()" class="btn btn-primary">Calculate</button>
        </div>
      </div>
      <div *ngIf="areCalculationCompleted()">
        {{contract.dailyGrossEarnings}}
      </div>
    </form>
  `
})
export class ContractComponent {

  country: String = "PL";
  salary: String = "";
  contract: ContractDto;

  areCalculationCompleted(): boolean {
    return this.contract != null;
  }

  constructor(private contractService: ContractService) {
  }

  calculateDailyNetEarningsInPLN() {
    this.contractService.getDailyNetEarningsInPLN(new ContractDto(this.country, this.salary)).subscribe(contract => {
      this.contract = contract;
    })
  }
}
