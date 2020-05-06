import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Bazarv3SharedModule } from 'app/shared/shared.module';
import { ProduitUniteComponent } from './produit-unite.component';
import { ProduitUniteDetailComponent } from './produit-unite-detail.component';
import { ProduitUniteUpdateComponent } from './produit-unite-update.component';
import { ProduitUniteDeleteDialogComponent } from './produit-unite-delete-dialog.component';
import { produitUniteRoute } from './produit-unite.route';

@NgModule({
  imports: [Bazarv3SharedModule, RouterModule.forChild(produitUniteRoute)],
  declarations: [ProduitUniteComponent, ProduitUniteDetailComponent, ProduitUniteUpdateComponent, ProduitUniteDeleteDialogComponent],
  entryComponents: [ProduitUniteDeleteDialogComponent]
})
export class Bazarv3ProduitUniteModule {}
