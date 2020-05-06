import { Moment } from 'moment';
import { CatClient } from 'app/shared/model/enumerations/cat-client.model';

export interface IRemise {
  id?: number;
  categorieClient?: CatClient;
  prixUnitaire?: number;
  remiseCategorie?: number;
  remisePromo?: number;
  cumulable?: boolean;
  debutPromo?: Moment;
  finPromo?: Moment;
  creeLe?: Moment;
  creePar?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  idProduitId?: number;
}

export class Remise implements IRemise {
  constructor(
    public id?: number,
    public categorieClient?: CatClient,
    public prixUnitaire?: number,
    public remiseCategorie?: number,
    public remisePromo?: number,
    public cumulable?: boolean,
    public debutPromo?: Moment,
    public finPromo?: Moment,
    public creeLe?: Moment,
    public creePar?: number,
    public modifieLe?: Moment,
    public modifiePar?: number,
    public idProduitId?: number
  ) {
    this.cumulable = this.cumulable || false;
  }
}
