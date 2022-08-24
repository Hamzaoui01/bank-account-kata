import { Operation } from "./operation.model";

export class Account {
  public number: string;
  public balance:number;
  public operations: Operation[];

  constructor(number: string, balance: number, operations: Operation[]) {
    this.number = number;
    this.balance = balance;
    this.operations = operations;
  }


}
