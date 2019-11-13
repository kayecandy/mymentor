export interface ICareerStone {
  id?: number;
  title?: string;
  fromDate?: Date;
  toDate?: Date;
  stillWorkingHere?: boolean;
  location?: string;
  description?: string;
  companyName?: string;
  companyId?: number;
  menteeId?: number;
}

export class CareerStone implements ICareerStone {
  constructor(
    public id?: number,
    public title?: string,
    public fromDate?: Date,
    public toDate?: Date,
    public stillWorkingHere?: boolean,
    public location?: string,
    public description?: string,
    public companyName?: string,
    public companyId?: number,
    public menteeId?: number
  ) {
    this.stillWorkingHere = this.stillWorkingHere || false;
  }
}
