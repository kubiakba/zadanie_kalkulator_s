import {Component} from '@angular/core';
import {ContractService} from "./contract.service";
import {ContractDto} from "./contract-dto";

@Component({
  selector: 'app-contract',
  template: `
    <div style="background-color: azure">
      <br/>
      <form>
        <div class="form-group">
          <div class="form-group col-md-2">
            <label for="earnings">Daily gross earnings</label>
            <input [(ngModel)]="salary" name="salary" type="text" class="form-control" id="earnings">
          </div>
        </div>
        <div class="form-group col-md-1">
          <label>Country</label>
          <select [(ngModel)]="country" name="country" id="inputState" class="form-control">
            <option selected>PL</option>
            <option>DE</option>
            <option>GB</option>
          </select>
        </div>
        <div class="form-group col-md-2">
          <button type="submit" (click)="calculateDailyNetEarningsInPLN()" class="btn btn-primary">Calculate</button>
        </div>
        <div class="form-group col-md-2" *ngIf="areCalculationCompleted()">
          <p>Daily net earnings in PLN : {{contract.dailyGrossEarnings}}</p>
        </div>
      </form>
    </div>
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
