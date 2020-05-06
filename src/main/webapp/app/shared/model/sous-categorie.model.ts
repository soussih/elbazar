import { Moment } from 'moment';
import { IProduit } from 'app/shared/model/produit.model';
import { IStock } from 'app/shared/model/stock.model';
import { IImages } from 'app/shared/model/images.model';

export interface ISousCategorie {
  id?: number;
  nom?: string;
  description?: string;
  position?: number;
  etat?: boolean;
  creeLe?: Moment;
  creePar?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  produits?: IProduit[];
  stocks?: IStock[];
  images?: IImages[];
  categorieNom?: string;
  categorieId?: number;
}

export class SousCategorie implements ISousCategorie {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public position?: number,
    public etat?: boolean,
    public creeLe?: Moment,
    public creePar?: number,
    public modifieLe?: Moment,
    public modifiePar?: number,
    public produits?: IProduit[],
    public stocks?: IStock[],
    public images?: IImages[],
    public categorieNom?: string,
    public categorieId?: number
  ) {
    this.etat = this.etat || false;
  }
}
