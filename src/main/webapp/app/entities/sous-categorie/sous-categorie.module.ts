import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Bazarv3SharedModule } from 'app/shared/shared.module';
import { SousCategorieComponent } from './sous-categorie.component';
import { SousCategorieDetailComponent } from './sous-categorie-detail.component';
import { SousCategorieUpdateComponent } from './sous-categorie-update.component';
import { SousCategorieDeleteDialogComponent } from './sous-categorie-delete-dialog.component';
import { sousCategorieRoute } from './sous-categorie.route';

@NgModule({
  imports: [Bazarv3SharedModule, RouterModule.forChild(sousCategorieRoute)],
  declarations: [SousCategorieComponent, SousCategorieDetailComponent, SousCategorieUpdateComponent, SousCategorieDeleteDialogComponent],
  entryComponents: [SousCategorieDeleteDialogComponent]
})
export class Bazarv3SousCategorieModule {}
