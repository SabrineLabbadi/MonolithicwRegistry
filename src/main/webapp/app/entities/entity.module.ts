import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'concert',
        loadChildren: () => import('./concert/concert.module').then(m => m.MonolithicwRegistryConcertModule),
      },
      {
        path: 'band',
        loadChildren: () => import('./band/band.module').then(m => m.MonolithicwRegistryBandModule),
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.MonolithicwRegistryCustomerModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MonolithicwRegistryEntityModule {}
