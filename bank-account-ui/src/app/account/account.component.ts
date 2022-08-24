import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account } from '../models/account.model';
import { BankAccountService } from '../service/bank-account.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  constructor(private bankService: BankAccountService, private route:ActivatedRoute) { }

  accounts:Account[]=[]
  selectedAccount:Account = {} as Account;
  displaytost=false;
  message!:string;

  ngOnInit(): void {
    let number = this.route.snapshot.paramMap.get("number");
    if(number!=null) {
      this.selectAccount(number);
      this.message = "transaction processed successfully on account: "+number;
      this.displaytost = true;
    }
    this.bankService.getAccountFromServer().subscribe(
      {
        next: (v) => {this.accounts=v},
        error: (e) => console.error(e)
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


  closeToast(){
    this.displaytost = false;
  }

}
