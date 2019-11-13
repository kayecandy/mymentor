export interface ILanguage {
  id?: number;
  name?: string;
  menteeId?: number;
}

export class Language implements ILanguage {
  constructor(public id?: number, public name?: string, public menteeId?: number) {}
}
