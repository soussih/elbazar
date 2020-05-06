import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'categorie',
        loadChildren: () => import('./categorie/categorie.module').then(m => m.Bazarv3CategorieModule)
      },
      {
        path: 'sous-categorie',
        loadChildren: () => import('./sous-categorie/sous-categorie.module').then(m => m.Bazarv3SousCategorieModule)
      },
      {
        path: 'produit',
        loadChildren: () => import('./produit/produit.module').then(m => m.Bazarv3ProduitModule)
      },
      {
        path: 'prod-enumeration',
        loadChildren: () => import('./prod-enumeration/prod-enumeration.module').then(m => m.Bazarv3ProdEnumerationModule)
      },
      {
        path: 'produit-unite',
        loadChildren: () => import('./produit-unite/produit-unite.module').then(m => m.Bazarv3ProduitUniteModule)
      },
      {
        path: 'stock',
        loadChildren: () => import('./stock/stock.module').then(m => m.Bazarv3StockModule)
      },
      {
        path: 'mouvement-stock',
        loadChildren: () => import('./mouvement-stock/mouvement-stock.module').then(m => m.Bazarv3MouvementStockModule)
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.Bazarv3ClientModule)
      },
      {
        path: 'adresse',
        loadChildren: () => import('./adresse/adresse.module').then(m => m.Bazarv3AdresseModule)
      },
      {
        path: 'commande',
        loadChildren: () => import('./commande/commande.module').then(m => m.Bazarv3CommandeModule)
      },
      {
        path: 'commande-lignes',
        loadChildren: () => import('./commande-lignes/commande-lignes.module').then(m => m.Bazarv3CommandeLignesModule)
      },
      {
        path: 'zone',
        loadChildren: () => import('./zone/zone.module').then(m => m.Bazarv3ZoneModule)
      },
      {
        path: 'livraison',
        loadChildren: () => import('./livraison/livraison.module').then(m => m.Bazarv3LivraisonModule)
      },
      {
        path: 'code-postaux',
        loadChildren: () => import('./code-postaux/code-postaux.module').then(m => m.Bazarv3CodePostauxModule)
      },
      {
        path: 'images',
        loadChildren: () => import('./images/images.module').then(m => m.Bazarv3ImagesModule)
      },
      {
        path: 'remise',
        loadChildren: () => import('./remise/remise.module').then(m => m.Bazarv3RemiseModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class Bazarv3EntityModule {}
