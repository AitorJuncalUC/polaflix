import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SeriesComponent } from './series/series.component';
import { FacturasComponent } from './facturas/facturas.component';

export const routes: Routes = [
    { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
    { path: 'series', component: SeriesComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'facturas', component: FacturasComponent },
];
