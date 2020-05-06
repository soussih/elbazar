export interface IProduitUnite {
  id?: number;
  prdunite?: string;
}

export class ProduitUnite implements IProduitUnite {
  constructor(public id?: number, public prdunite?: string) {}
}
