import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import SkillService from '../skill/skill.service';
import { ISkill } from '@/shared/model/skill.model';

import MenteeService from '../mentee/mentee.service';
import { IMentee } from '@/shared/model/mentee.model';

import AlertService from '@/shared/alert/alert.service';
import { IPossessedSkill, PossessedSkill } from '@/shared/model/possessed-skill.model';
import PossessedSkillService from './possessed-skill.service';

const validations: any = {
  possessedSkill: {
    topSkill: {}
  }
};

@Component({
  validations
})
export default class PossessedSkillUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('possessedSkillService') private possessedSkillService: () => PossessedSkillService;
  public possessedSkill: IPossessedSkill = new PossessedSkill();

  @Inject('skillService') private skillService: () => SkillService;

  public skills: ISkill[] = [];

  @Inject('menteeService') private menteeService: () => MenteeService;

  public mentees: IMentee[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.possessedSkillId) {
        vm.retrievePossessedSkill(to.params.possessedSkillId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.possessedSkill.id) {
      this.possessedSkillService()
        .update(this.possessedSkill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.possessedSkill.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.possessedSkillService()
        .create(this.possessedSkill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.possessedSkill.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePossessedSkill(possessedSkillId): void {
    this.possessedSkillService()
      .find(possessedSkillId)
      .then(res => {
        this.possessedSkill = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.skillService()
      .retrieve()
      .then(res => {
        this.skills = res.data;
      });
    this.menteeService()
      .retrieve()
      .then(res => {
        this.mentees = res.data;
      });
  }
}
