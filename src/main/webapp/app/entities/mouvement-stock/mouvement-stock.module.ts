import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Bazarv3SharedModule } from 'app/shared/shared.module';
import { MouvementStockComponent } from './mouvement-stock.component';
import { MouvementStockDetailComponent } from './mouvement-stock-detail.component';
import { MouvementStockUpdateComponent } from './mouvement-stock-update.component';
import { MouvementStockDeleteDialogComponent } from './mouvement-stock-delete-dialog.component';
import { mouvementStockRoute } from './mouvement-stock.route';

@NgModule({
  imports: [Bazarv3SharedModule, RouterModule.forChild(mouvementStockRoute)],
  declarations: [
    MouvementStockComponent,
    MouvementStockDetailComponent,
    MouvementStockUpdateComponent,
    MouvementStockDeleteDialogComponent
  ],
  entryComponents: [MouvementStockDeleteDialogComponent]
})
export class Bazarv3MouvementStockModule {}
