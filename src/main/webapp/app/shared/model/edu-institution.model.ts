export interface IEduInstitution {
  id?: number;
  name?: string;
  description?: string;
  url?: string;
  addressCity?: string;
  addressId?: number;
}

export class EduInstitution implements IEduInstitution {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public url?: string,
    public addressCity?: string,
    public addressId?: number
  ) {}
}
