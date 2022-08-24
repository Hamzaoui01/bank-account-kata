import { Component, OnInit } from '@angular/core';
import { Account } from '../models/account.model';
import { BankAccountService } from '../service/bank-account.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  constructor(private bankService: BankAccountService) { }

  accounts:Account[]=[]
  selectedAccount:Account = {} as Account;

  ngOnInit(): void {
    this.bankService.getAccountFromServer().subscribe(
      {
        next: (v) => {this.accounts=v},
        error: (e) => console.error(e),
        complete: ()=> console.info("complete")
      }
    )
  }

  selectAccount(number : string){
    this.bankService.getAccountByNumberFromServer(number)
    .subscribe(
      {
        next: (v) => {this.selectedAccount=v},
        error: (e) => console.error(e),
        complete: ()=> console.info("complete")
      }
    );
  }

}
