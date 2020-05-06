import { Moment } from 'moment';
import { IStock } from 'app/shared/model/stock.model';
import { IMouvementStock } from 'app/shared/model/mouvement-stock.model';
import { ICommandeLignes } from 'app/shared/model/commande-lignes.model';
import { IRemise } from 'app/shared/model/remise.model';
import { IImages } from 'app/shared/model/images.model';
import { SourcePrd } from 'app/shared/model/enumerations/source-prd.model';

export interface IProduit {
  id?: number;
  nom?: string;
  codeBarre?: number;
  description?: string;
  etat?: boolean;
  unite?: string;
  marque?: string;
  nature?: string;
  stockMinimum?: number;
  quantiteVenteMax?: number;
  datePremption?: Moment;
  prixUnitaireHT?: number;
  tauxTva?: number;
  prixTtc?: number;
  sourceProduit?: SourcePrd;
  horsStock?: boolean;
  rating?: string;
  remise?: number;
  creeLe?: Moment;
  creePar?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  stocks?: IStock[];
  mouvementStocks?: IMouvementStock[];
  commandeLignes?: ICommandeLignes[];
  remises?: IRemise[];
  images?: IImages[];
  sousCategorieNom?: string;
  sousCategorieId?: number;
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public nom?: string,
    public codeBarre?: number,
    public description?: string,
    public etat?: boolean,
    public unite?: string,
    public marque?: string,
    public nature?: string,
    public stockMinimum?: number,
    public quantiteVenteMax?: number,
    public datePremption?: Moment,
    public prixUnitaireHT?: number,
    public tauxTva?: number,
    public prixTtc?: number,
    public sourceProduit?: SourcePrd,
    public horsStock?: boolean,
    public rating?: string,
    public remise?: number,
    public creeLe?: Moment,
    public creePar?: number,
    public modifieLe?: Moment,
    public modifiePar?: number,
    public stocks?: IStock[],
    public mouvementStocks?: IMouvementStock[],
    public commandeLignes?: ICommandeLignes[],
    public remises?: IRemise[],
    public images?: IImages[],
    public sousCategorieNom?: string,
    public sousCategorieId?: number
  ) {
    this.etat = this.etat || false;
    this.horsStock = this.horsStock || false;
  }
}
