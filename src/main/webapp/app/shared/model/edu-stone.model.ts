export interface IEduStone {
  id?: number;
  degree?: string;
  grade?: string;
  fromDate?: Date;
  toDate?: Date;
  description?: string;
  activities?: string;
  schoolName?: string;
  schoolId?: number;
  menteeId?: number;
}

export class EduStone implements IEduStone {
  constructor(
    public id?: number,
    public degree?: string,
    public grade?: string,
    public fromDate?: Date,
    public toDate?: Date,
    public description?: string,
    public activities?: string,
    public schoolName?: string,
    public schoolId?: number,
    public menteeId?: number
  ) {}
}
