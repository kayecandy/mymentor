import { ICareerStone } from '@/shared/model/career-stone.model';
import { IEduStone } from '@/shared/model/edu-stone.model';
import { ICertificate } from '@/shared/model/certificate.model';
import { ILanguage } from '@/shared/model/language.model';
import { IPossessedSkill } from '@/shared/model/possessed-skill.model';

export interface IMentee {
  id?: number;
  mobilePhone?: string;
  landline?: string;
  profileVisibleInInternet?: boolean;
  ownWebsiteUrl?: string;
  xingProfileUrl?: string;
  linkedInProfileUrl?: string;
  userLogin?: string;
  userId?: number;
  addressId?: number;
  careerStones?: ICareerStone[];
  eduStones?: IEduStone[];
  certificates?: ICertificate[];
  languages?: ILanguage[];
  possessedSkills?: IPossessedSkill[];
}

export class Mentee implements IMentee {
  constructor(
    public id?: number,
    public mobilePhone?: string,
    public landline?: string,
    public profileVisibleInInternet?: boolean,
    public ownWebsiteUrl?: string,
    public xingProfileUrl?: string,
    public linkedInProfileUrl?: string,
    public userLogin?: string,
    public userId?: number,
    public addressId?: number,
    public careerStones?: ICareerStone[],
    public eduStones?: IEduStone[],
    public certificates?: ICertificate[],
    public languages?: ILanguage[],
    public possessedSkills?: IPossessedSkill[]
  ) {
    this.profileVisibleInInternet = this.profileVisibleInInternet || false;
  }
}
