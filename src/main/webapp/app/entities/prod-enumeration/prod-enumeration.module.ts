import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Bazarv3SharedModule } from 'app/shared/shared.module';
import { ProdEnumerationComponent } from './prod-enumeration.component';
import { ProdEnumerationDetailComponent } from './prod-enumeration-detail.component';
import { ProdEnumerationUpdateComponent } from './prod-enumeration-update.component';
import { ProdEnumerationDeleteDialogComponent } from './prod-enumeration-delete-dialog.component';
import { prodEnumerationRoute } from './prod-enumeration.route';

@NgModule({
  imports: [Bazarv3SharedModule, RouterModule.forChild(prodEnumerationRoute)],
  declarations: [
    ProdEnumerationComponent,
    ProdEnumerationDetailComponent,
    ProdEnumerationUpdateComponent,
    ProdEnumerationDeleteDialogComponent
  ],
  entryComponents: [ProdEnumerationDeleteDialogComponent]
})
export class Bazarv3ProdEnumerationModule {}
