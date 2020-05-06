import { ProdEnum } from 'app/shared/model/enumerations/prod-enum.model';

export interface IProdEnumeration {
  id?: number;
  type?: ProdEnum;
  nom?: string;
}

export class ProdEnumeration implements IProdEnumeration {
  constructor(public id?: number, public type?: ProdEnum, public nom?: string) {}
}
