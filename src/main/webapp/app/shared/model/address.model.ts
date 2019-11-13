export interface IAddress {
  id?: number;
  street?: string;
  streetNumber?: string;
  secondLine?: string;
  postcode?: string;
  city?: string;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public street?: string,
    public streetNumber?: string,
    public secondLine?: string,
    public postcode?: string,
    public city?: string
  ) {}
}
