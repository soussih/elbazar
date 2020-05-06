import { Moment } from 'moment';
import { IAdresse } from 'app/shared/model/adresse.model';
import { ICommande } from 'app/shared/model/commande.model';
import { Civilite } from 'app/shared/model/enumerations/civilite.model';
import { RegMod } from 'app/shared/model/enumerations/reg-mod.model';
import { CatClient } from 'app/shared/model/enumerations/cat-client.model';

export interface IClient {
  id?: number;
  civilite?: Civilite;
  nom?: string;
  prenom?: string;
  dateDeNaissance?: Moment;
  email?: string;
  mobile?: number;
  reglement?: RegMod;
  etat?: boolean;
  derniereVisite?: Moment;
  totalAchat?: number;
  categorie?: CatClient;
  pointsFidelite?: number;
  creeLe?: Moment;
  creePar?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  adresses?: IAdresse[];
  commandes?: ICommande[];
  clientId?: number;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public civilite?: Civilite,
    public nom?: string,
    public prenom?: string,
    public dateDeNaissance?: Moment,
    public email?: string,
    public mobile?: number,
    public reglement?: RegMod,
    public etat?: boolean,
    public derniereVisite?: Moment,
    public totalAchat?: number,
    public categorie?: CatClient,
    public pointsFidelite?: number,
    public creeLe?: Moment,
    public creePar?: number,
    public modifieLe?: Moment,
    public modifiePar?: number,
    public adresses?: IAdresse[],
    public commandes?: ICommande[],
    public clientId?: number
  ) {
    this.etat = this.etat || false;
  }
}
