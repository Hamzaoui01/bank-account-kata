import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './account/account.component';
import { OperationComponent } from './operation/operation.component';

const routes: Routes = [
  {path:'operation/debit',component:OperationComponent},
  {path:'operation/credit',component:OperationComponent},
  {path:'accounts',component:AccountComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
