import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Bazarv3SharedModule } from 'app/shared/shared.module';
import { LivraisonComponent } from './livraison.component';
import { LivraisonDetailComponent } from './livraison-detail.component';
import { LivraisonUpdateComponent } from './livraison-update.component';
import { LivraisonDeleteDialogComponent } from './livraison-delete-dialog.component';
import { livraisonRoute } from './livraison.route';

@NgModule({
  imports: [Bazarv3SharedModule, RouterModule.forChild(livraisonRoute)],
  declarations: [LivraisonComponent, LivraisonDetailComponent, LivraisonUpdateComponent, LivraisonDeleteDialogComponent],
  entryComponents: [LivraisonDeleteDialogComponent]
})
export class Bazarv3LivraisonModule {}
