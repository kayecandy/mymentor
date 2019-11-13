import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import EduInstitutionService from '../edu-institution/edu-institution.service';
import { IEduInstitution } from '@/shared/model/edu-institution.model';

import MenteeService from '../mentee/mentee.service';
import { IMentee } from '@/shared/model/mentee.model';

import AlertService from '@/shared/alert/alert.service';
import { IEduStone, EduStone } from '@/shared/model/edu-stone.model';
import EduStoneService from './edu-stone.service';

const validations: any = {
  eduStone: {
    degree: {
      required,
      maxLength: maxLength(127)
    },
    grade: {
      maxLength: maxLength(31)
    },
    fromDate: {
      required
    },
    toDate: {
      required
    },
    description: {
      maxLength: maxLength(1023)
    },
    activities: {
      maxLength: maxLength(1023)
    }
  }
};

@Component({
  validations
})
export default class EduStoneUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('eduStoneService') private eduStoneService: () => EduStoneService;
  public eduStone: IEduStone = new EduStone();

  @Inject('eduInstitutionService') private eduInstitutionService: () => EduInstitutionService;

  public eduInstitutions: IEduInstitution[] = [];

  @Inject('menteeService') private menteeService: () => MenteeService;

  public mentees: IMentee[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.eduStoneId) {
        vm.retrieveEduStone(to.params.eduStoneId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.eduStone.id) {
      this.eduStoneService()
        .update(this.eduStone)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.eduStone.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.eduStoneService()
        .create(this.eduStone)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.eduStone.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveEduStone(eduStoneId): void {
    this.eduStoneService()
      .find(eduStoneId)
      .then(res => {
        this.eduStone = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.eduInstitutionService()
      .retrieve()
      .then(res => {
        this.eduInstitutions = res.data;
      });
    this.menteeService()
      .retrieve()
      .then(res => {
        this.mentees = res.data;
      });
  }
}
