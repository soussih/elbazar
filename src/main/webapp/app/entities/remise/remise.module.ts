import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Bazarv3SharedModule } from 'app/shared/shared.module';
import { RemiseComponent } from './remise.component';
import { RemiseDetailComponent } from './remise-detail.component';
import { RemiseUpdateComponent } from './remise-update.component';
import { RemiseDeleteDialogComponent } from './remise-delete-dialog.component';
import { remiseRoute } from './remise.route';

@NgModule({
  imports: [Bazarv3SharedModule, RouterModule.forChild(remiseRoute)],
  declarations: [RemiseComponent, RemiseDetailComponent, RemiseUpdateComponent, RemiseDeleteDialogComponent],
  entryComponents: [RemiseDeleteDialogComponent]
})
export class Bazarv3RemiseModule {}
