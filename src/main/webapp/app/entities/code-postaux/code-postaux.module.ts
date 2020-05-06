import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Bazarv3SharedModule } from 'app/shared/shared.module';
import { CodePostauxComponent } from './code-postaux.component';
import { CodePostauxDetailComponent } from './code-postaux-detail.component';
import { CodePostauxUpdateComponent } from './code-postaux-update.component';
import { CodePostauxDeleteDialogComponent } from './code-postaux-delete-dialog.component';
import { codePostauxRoute } from './code-postaux.route';

@NgModule({
  imports: [Bazarv3SharedModule, RouterModule.forChild(codePostauxRoute)],
  declarations: [CodePostauxComponent, CodePostauxDetailComponent, CodePostauxUpdateComponent, CodePostauxDeleteDialogComponent],
  entryComponents: [CodePostauxDeleteDialogComponent]
})
export class Bazarv3CodePostauxModule {}
