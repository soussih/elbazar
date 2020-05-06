import { Moment } from 'moment';
import { ISousCategorie } from 'app/shared/model/sous-categorie.model';
import { IStock } from 'app/shared/model/stock.model';
import { IImages } from 'app/shared/model/images.model';

export interface ICategorie {
  id?: number;
  nom?: string;
  description?: string;
  position?: number;
  etat?: boolean;
  creeLe?: Moment;
  creePar?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  sousCategories?: ISousCategorie[];
  stocks?: IStock[];
  images?: IImages[];
}

export class Categorie implements ICategorie {
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
    public sousCategories?: ISousCategorie[],
    public stocks?: IStock[],
    public images?: IImages[]
  ) {
    this.etat = this.etat || false;
  }
}
