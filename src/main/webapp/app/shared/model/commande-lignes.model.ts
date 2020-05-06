import { Moment } from 'moment';

export interface ICommandeLignes {
  id?: number;
  quantite?: number;
  prixHT?: number;
  tva?: number;
  prixTTC?: number;
  creeLe?: Moment;
  creePar?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  commandeId?: number;
  idProduitId?: number;
}

export class CommandeLignes implements ICommandeLignes {
  constructor(
    public id?: number,
    public quantite?: number,
    public prixHT?: number,
    public tva?: number,
    public prixTTC?: number,
    public creeLe?: Moment,
    public creePar?: number,
    public modifieLe?: Moment,
    public modifiePar?: number,
    public commandeId?: number,
    public idProduitId?: number
  ) {}
}
