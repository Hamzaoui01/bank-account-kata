export class Operation {
  public id:number;
  public type: string;
  public amount: number;
  public dateTime: string;

  constructor(id:number, type:string,amount: number,dateTime: string ){
    this.id = id;
    this.type = type;
    this.amount = amount;
    this.dateTime = dateTime;
  }
}
