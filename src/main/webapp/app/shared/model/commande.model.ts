import { Moment } from 'moment';
import { IMouvementStock } from 'app/shared/model/mouvement-stock.model';
import { ICommandeLignes } from 'app/shared/model/commande-lignes.model';
import { StatCmd } from 'app/shared/model/enumerations/stat-cmd.model';
import { NaturePiece } from 'app/shared/model/enumerations/nature-piece.model';

export interface ICommande {
  id?: number;
  reference?: string;
  date?: Moment;
  statut?: StatCmd;
  naturePiece?: NaturePiece;
  totalHT?: number;
  totalTVA?: number;
  totalRemise?: number;
  totTTC?: number;
  zone?: string;
  dateLivraison?: Moment;
  creeLe?: Moment;
  creePar?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  mouvementStocks?: IMouvementStock[];
  commandeLignes?: ICommandeLignes[];
  idClientId?: number;
  idAdresseId?: number;
}

export class Commande implements ICommande {
  constructor(
    public id?: number,
    public reference?: string,
    public date?: Moment,
    public statut?: StatCmd,
    public naturePiece?: NaturePiece,
    public totalHT?: number,
    public totalTVA?: number,
    public totalRemise?: number,
    public totTTC?: number,
    public zone?: string,
    public dateLivraison?: Moment,
    public creeLe?: Moment,
    public creePar?: number,
    public modifieLe?: Moment,
    public modifiePar?: number,
    public mouvementStocks?: IMouvementStock[],
    public commandeLignes?: ICommandeLignes[],
    public idClientId?: number,
    public idAdresseId?: number
  ) {}
}
