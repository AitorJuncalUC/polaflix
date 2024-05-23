import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SeriesComponent } from './series/series.component';
import { FacturasComponent } from './facturas/facturas.component';
import { SerieDetailsComponent } from './serie-details/serie-details.component';

export const routes: Routes = [
    { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
    { path: 'series', component: SeriesComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'facturas', component: FacturasComponent },
    { path: 'serie-details/:id', component: SerieDetailsComponent }
];
