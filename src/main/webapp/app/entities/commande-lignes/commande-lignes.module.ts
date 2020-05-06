import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Bazarv3SharedModule } from 'app/shared/shared.module';
import { CommandeLignesComponent } from './commande-lignes.component';
import { CommandeLignesDetailComponent } from './commande-lignes-detail.component';
import { CommandeLignesUpdateComponent } from './commande-lignes-update.component';
import { CommandeLignesDeleteDialogComponent } from './commande-lignes-delete-dialog.component';
import { commandeLignesRoute } from './commande-lignes.route';

@NgModule({
  imports: [Bazarv3SharedModule, RouterModule.forChild(commandeLignesRoute)],
  declarations: [
    CommandeLignesComponent,
    CommandeLignesDetailComponent,
    CommandeLignesUpdateComponent,
    CommandeLignesDeleteDialogComponent
  ],
  entryComponents: [CommandeLignesDeleteDialogComponent]
})
export class Bazarv3CommandeLignesModule {}
