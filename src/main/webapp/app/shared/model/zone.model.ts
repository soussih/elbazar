import { Moment } from 'moment';
import { IAdresse } from 'app/shared/model/adresse.model';
import { ILivraison } from 'app/shared/model/livraison.model';
import { ICodePostaux } from 'app/shared/model/code-postaux.model';

export interface IZone {
  id?: number;
  nom?: string;
  creeLe?: Moment;
  creePar?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  adresses?: IAdresse[];
  livraisons?: ILivraison[];
  codePostauxes?: ICodePostaux[];
}

export class Zone implements IZone {
  constructor(
    public id?: number,
    public nom?: string,
    public creeLe?: Moment,
    public creePar?: number,
    public modifieLe?: Moment,
    public modifiePar?: number,
    public adresses?: IAdresse[],
    public livraisons?: ILivraison[],
    public codePostauxes?: ICodePostaux[]
  ) {}
}
