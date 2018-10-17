import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/index";
import {ContractDto} from "./contract-dto";

@Injectable({
  providedIn: 'root'
})
export class ContractService {

  contract_url = "/contract";

  constructor(private http: HttpClient) {
  }

  public getDailyNetEarningsInPLN(contract: ContractDto): Observable<ContractDto> {
    return this.http.post<ContractDto>(this.contract_url, contract)
  }
}
