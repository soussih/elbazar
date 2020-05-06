import { Moment } from 'moment';
import { IAdresse } from 'app/shared/model/adresse.model';

export interface ICodePostaux {
  id?: number;
  gouvernorat?: string;
  ville?: string;
  localite?: string;
  codePostal?: number;
  modifieLe?: Moment;
  modifiePar?: number;
  adresses?: IAdresse[];
  zoneNom?: string;
  zoneId?: number;
}

export class CodePostaux implements ICodePostaux {
  constructor(
    public id?: number,
    public gouvernorat?: string,
    public ville?: string,
    public localite?: string,
    public codePostal?: number,
    public modifieLe?: Moment,
    public modifiePar?: number,
    public adresses?: IAdresse[],
    public zoneNom?: string,
    public zoneId?: number
  ) {}
}
