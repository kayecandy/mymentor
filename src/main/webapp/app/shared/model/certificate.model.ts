export interface ICertificate {
  id?: number;
  name?: string;
  issueDate?: Date;
  expireDate?: Date;
  url?: string;
  institutionName?: string;
  institutionId?: number;
  menteeId?: number;
}

export class Certificate implements ICertificate {
  constructor(
    public id?: number,
    public name?: string,
    public issueDate?: Date,
    public expireDate?: Date,
    public url?: string,
    public institutionName?: string,
    public institutionId?: number,
    public menteeId?: number
  ) {}
}
