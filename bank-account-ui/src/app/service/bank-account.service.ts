import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Account } from '../models/account.model';


@Injectable({
  providedIn: 'root'
})
export class BankAccountService {

  constructor(private http: HttpClient) { }

  getAccountFromServer() {
    return this.http.get<Account[]>("http://localhost:8080/accounts");
  }

  getAccountByNumberFromServer(number:string) {
    return this.http.get<Account>("http://localhost:8080/accounts/"+number)
  }

}
