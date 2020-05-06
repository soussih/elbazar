import { Moment } from 'moment';

export interface IStock {
  id?: number;
  stockPhysique?: number;
  stockDisponible?: number;
  stockReserve?: number;
  stockCommande?: number;
  derniereEntre?: Moment;
  derniereSortie?: Moment;
  alerteStock?: boolean;
  creeLe?: Moment;
  creePar?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  idProduitId?: number;
  idCategorieId?: number;
  idSousCategorieId?: number;
}

export class Stock implements IStock {
  constructor(
    public id?: number,
    public stockPhysique?: number,
    public stockDisponible?: number,
    public stockReserve?: number,
    public stockCommande?: number,
    public derniereEntre?: Moment,
    public derniereSortie?: Moment,
    public alerteStock?: boolean,
    public creeLe?: Moment,
    public creePar?: number,
    public modifieLe?: Moment,
    public modifiePar?: number,
    public idProduitId?: number,
    public idCategorieId?: number,
    public idSousCategorieId?: number
  ) {
    this.alerteStock = this.alerteStock || false;
  }
}
