import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { Bazarv3SharedModule } from 'app/shared/shared.module';
import { Bazarv3CoreModule } from 'app/core/core.module';
import { Bazarv3AppRoutingModule } from './app-routing.module';
import { Bazarv3HomeModule } from './home/home.module';
import { Bazarv3EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    Bazarv3SharedModule,
    Bazarv3CoreModule,
    Bazarv3HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    Bazarv3EntityModule,
    Bazarv3AppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class Bazarv3AppModule {}
