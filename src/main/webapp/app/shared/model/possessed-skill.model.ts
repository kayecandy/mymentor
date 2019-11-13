import { IMentee } from '@/shared/model/mentee.model';

export interface IPossessedSkill {
  id?: number;
  topSkill?: boolean;
  skillName?: string;
  skillId?: number;
  mentees?: IMentee[];
}

export class PossessedSkill implements IPossessedSkill {
  constructor(
    public id?: number,
    public topSkill?: boolean,
    public skillName?: string,
    public skillId?: number,
    public mentees?: IMentee[]
  ) {
    this.topSkill = this.topSkill || false;
  }
}
