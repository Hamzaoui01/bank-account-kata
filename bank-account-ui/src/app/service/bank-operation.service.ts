import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Account } from '../models/account.model';

@Injectable({
  providedIn: 'root'
})
export class BankOperationService {

  constructor(private http: HttpClient) { }


  depositMoney(numberAccount:string, amount:number){
    return this.http.post<Account>("http://localhost:8080/operations/debit",{"accountNumber":numberAccount,"amount":amount});
  }

  withdrawMoney(numberAccount:string, amount:number){
    return this.http.post<Account>("http://localhost:8080/operations/credit",{"accountNumber":numberAccount,"amount":amount});
  }
}
