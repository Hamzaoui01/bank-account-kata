import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BankOperationService } from '../service/bank-operation.service';

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.css']
})
export class OperationComponent implements OnInit {

  constructor(private bankOperationService : BankOperationService,private router:Router) { }

  title:string="";

  ngOnInit(): void {
    console.log(this.router.url)
    this.title = this.isDebit()?"Deposit Money":"Withdraw Moeny";
  }

  isDebit(){
    return this.router.url.includes("debit");
  }

  onSubmit(formValues: any) {

    console.log(formValues)

    if(this.isDebit()){
      this.bankOperationService.depositMoney(formValues.number,formValues.amount).subscribe({
        next: (v) => {this.router.navigate(["/accounts",formValues.number])},
        error: (e) => {console.error(e)}
      });
    }
    else{
      this.bankOperationService.withdrawMoney(formValues.number,formValues.amount).subscribe({
        next: (v) => {this.router.navigate(["/accounts",formValues.number])},
        error: (e) => {console.error(e)}
      });
    }

  }

}
