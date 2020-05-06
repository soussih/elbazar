import { Moment } from 'moment';
import { CatClient } from 'app/shared/model/enumerations/cat-client.model';

export interface ILivraison {
  id?: number;
  categorieClient?: CatClient;
  intervalValeur?: number;
  frais?: number;
  creeLe?: Moment;
  creePar?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  zoneNom?: string;
  zoneId?: number;
}

export class Livraison implements ILivraison {
  constructor(
    public id?: number,
    public categorieClient?: CatClient,
    public intervalValeur?: number,
    public frais?: number,
    public creeLe?: Moment,
    public creePar?: number,
    public modifieLe?: Moment,
    public modifiePar?: number,
    public zoneNom?: string,
    public zoneId?: number
  ) {}
}
