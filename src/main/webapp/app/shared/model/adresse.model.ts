import { Moment } from 'moment';
import { IClient } from 'app/shared/model/client.model';
import { ICommande } from 'app/shared/model/commande.model';

export interface IAdresse {
  id?: number;
  principale?: boolean;
  nom?: string;
  prenom?: string;
  adresseLigne1?: number;
  adresseLigne2?: number;
  gouvernorat?: string;
  ville?: string;
  cite?: string;
  indication?: string;
  telephone?: number;
  mobile?: number;
  creeLe?: Moment;
  creePar?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  clients?: IClient[];
  commandes?: ICommande[];
  clientId?: number;
  zoneId?: number;
  codePostalCodePostal?: string;
  codePostalId?: number;
}

export class Adresse implements IAdresse {
  constructor(
    public id?: number,
    public principale?: boolean,
    public nom?: string,
    public prenom?: string,
    public adresseLigne1?: number,
    public adresseLigne2?: number,
    public gouvernorat?: string,
    public ville?: string,
    public cite?: string,
    public indication?: string,
    public telephone?: number,
    public mobile?: number,
    public creeLe?: Moment,
    public creePar?: number,
    public modifieLe?: Moment,
    public modifiePar?: number,
    public clients?: IClient[],
    public commandes?: ICommande[],
    public clientId?: number,
    public zoneId?: number,
    public codePostalCodePostal?: string,
    public codePostalId?: number
  ) {
    this.principale = this.principale || false;
  }
}
