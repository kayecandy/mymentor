import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ISkill, Skill } from '@/shared/model/skill.model';
import SkillService from './skill.service';

const validations: any = {
  skill: {
    name: {
      required,
      maxLength: maxLength(127)
    },
    type: {
      required
    },
    description: {
      maxLength: maxLength(1023)
    }
  }
};

@Component({
  validations
})
export default class SkillUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('skillService') private skillService: () => SkillService;
  public skill: ISkill = new Skill();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.skillId) {
        vm.retrieveSkill(to.params.skillId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.skill.id) {
      this.skillService()
        .update(this.skill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.skill.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.skillService()
        .create(this.skill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.skill.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveSkill(skillId): void {
    this.skillService()
      .find(skillId)
      .then(res => {
        this.skill = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
