import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { StoreModule } from '@ngrx/store';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { SimulationComponent } from './simulation/simulation.component';
import { SenderComponent } from './simulation/sender/sender.component';
import { ListenerComponent } from './simulation/listener/listener.component';
import { reducers } from './store/app.reducers';
import { EffectsModule } from '@ngrx/effects';
import { SimulationEffects } from './simulation/store/simulation.effects';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    SimulationComponent,
    SenderComponent,
    ListenerComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    StoreModule.forRoot(reducers),
    EffectsModule.forRoot([SimulationEffects])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
