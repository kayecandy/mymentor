export const enum SkillType {
  INDUSTRY_KNOWLEDGE = 'INDUSTRY_KNOWLEDGE',
  TOOLS_TECHNOLOGIES = 'TOOLS_TECHNOLOGIES',
  INTERPERSONAL = 'INTERPERSONAL',
  OTHER = 'OTHER'
}

export interface ISkill {
  id?: number;
  name?: string;
  type?: SkillType;
  description?: string;
}

export class Skill implements ISkill {
  constructor(public id?: number, public name?: string, public type?: SkillType, public description?: string) {}
}
