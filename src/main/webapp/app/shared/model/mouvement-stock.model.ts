import { Moment } from 'moment';
import { TypeMvt } from 'app/shared/model/enumerations/type-mvt.model';

export interface IMouvementStock {
  id?: number;
  type?: TypeMvt;
  date?: Moment;
  sens?: number;
  quantite?: number;
  creeLe?: Moment;
  creePar?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  idProduitId?: number;
  refCommandeReference?: string;
  refCommandeId?: number;
}

export class MouvementStock implements IMouvementStock {
  constructor(
    public id?: number,
    public type?: TypeMvt,
    public date?: Moment,
    public sens?: number,
    public quantite?: number,
    public creeLe?: Moment,
    public creePar?: number,
    public modifieLe?: Moment,
    public modifiePar?: number,
    public idProduitId?: number,
    public refCommandeReference?: string,
    public refCommandeId?: number
  ) {}
}
