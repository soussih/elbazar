import { ImgType } from 'app/shared/model/enumerations/img-type.model';

export interface IImages {
  id?: number;
  type?: ImgType;
  imageContentType?: string;
  image?: any;
  idProduitId?: number;
  idCategorieId?: number;
  idSousCategorieId?: number;
}

export class Images implements IImages {
  constructor(
    public id?: number,
    public type?: ImgType,
    public imageContentType?: string,
    public image?: any,
    public idProduitId?: number,
    public idCategorieId?: number,
    public idSousCategorieId?: number
  ) {}
}
