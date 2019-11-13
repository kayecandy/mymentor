import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import AddressService from '../address/address.service';
import { IAddress } from '@/shared/model/address.model';

import CareerStoneService from '../career-stone/career-stone.service';
import { ICareerStone } from '@/shared/model/career-stone.model';

import EduStoneService from '../edu-stone/edu-stone.service';
import { IEduStone } from '@/shared/model/edu-stone.model';

import CertificateService from '../certificate/certificate.service';
import { ICertificate } from '@/shared/model/certificate.model';

import LanguageService from '../language/language.service';
import { ILanguage } from '@/shared/model/language.model';

import PossessedSkillService from '../possessed-skill/possessed-skill.service';
import { IPossessedSkill } from '@/shared/model/possessed-skill.model';

import AlertService from '@/shared/alert/alert.service';
import { IMentee, Mentee } from '@/shared/model/mentee.model';
import MenteeService from './mentee.service';

const validations: any = {
  mentee: {
    mobilePhone: {},
    landline: {},
    profileVisibleInInternet: {},
    ownWebsiteUrl: {
      maxLength: maxLength(255)
    },
    xingProfileUrl: {
      maxLength: maxLength(255)
    },
    linkedInProfileUrl: {
      maxLength: maxLength(255)
    }
  }
};

@Component({
  validations
})
export default class MenteeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('menteeService') private menteeService: () => MenteeService;
  public mentee: IMentee = new Mentee();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('addressService') private addressService: () => AddressService;

  public addresses: IAddress[] = [];

  @Inject('careerStoneService') private careerStoneService: () => CareerStoneService;

  public careerStones: ICareerStone[] = [];

  @Inject('eduStoneService') private eduStoneService: () => EduStoneService;

  public eduStones: IEduStone[] = [];

  @Inject('certificateService') private certificateService: () => CertificateService;

  public certificates: ICertificate[] = [];

  @Inject('languageService') private languageService: () => LanguageService;

  public languages: ILanguage[] = [];

  @Inject('possessedSkillService') private possessedSkillService: () => PossessedSkillService;

  public possessedSkills: IPossessedSkill[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.menteeId) {
        vm.retrieveMentee(to.params.menteeId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.mentee.possessedSkills = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.mentee.id) {
      this.menteeService()
        .update(this.mentee)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.mentee.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.menteeService()
        .create(this.mentee)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.mentee.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMentee(menteeId): void {
    this.menteeService()
      .find(menteeId)
      .then(res => {
        this.mentee = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.addressService()
      .retrieve()
      .then(res => {
        this.addresses = res.data;
      });
    this.careerStoneService()
      .retrieve()
      .then(res => {
        this.careerStones = res.data;
      });
    this.eduStoneService()
      .retrieve()
      .then(res => {
        this.eduStones = res.data;
      });
    this.certificateService()
      .retrieve()
      .then(res => {
        this.certificates = res.data;
      });
    this.languageService()
      .retrieve()
      .then(res => {
        this.languages = res.data;
      });
    this.possessedSkillService()
      .retrieve()
      .then(res => {
        this.possessedSkills = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
