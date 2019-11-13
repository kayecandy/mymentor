import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompanyService from '../company/company.service';
import { ICompany } from '@/shared/model/company.model';

import MenteeService from '../mentee/mentee.service';
import { IMentee } from '@/shared/model/mentee.model';

import AlertService from '@/shared/alert/alert.service';
import { ICareerStone, CareerStone } from '@/shared/model/career-stone.model';
import CareerStoneService from './career-stone.service';

const validations: any = {
  careerStone: {
    title: {
      required,
      maxLength: maxLength(127)
    },
    fromDate: {
      required
    },
    toDate: {},
    stillWorkingHere: {},
    location: {},
    description: {
      maxLength: maxLength(1023)
    }
  }
};

@Component({
  validations
})
export default class CareerStoneUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('careerStoneService') private careerStoneService: () => CareerStoneService;
  public careerStone: ICareerStone = new CareerStone();

  @Inject('companyService') private companyService: () => CompanyService;

  public companies: ICompany[] = [];

  @Inject('menteeService') private menteeService: () => MenteeService;

  public mentees: IMentee[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.careerStoneId) {
        vm.retrieveCareerStone(to.params.careerStoneId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.careerStone.id) {
      this.careerStoneService()
        .update(this.careerStone)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.careerStone.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.careerStoneService()
        .create(this.careerStone)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.careerStone.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCareerStone(careerStoneId): void {
    this.careerStoneService()
      .find(careerStoneId)
      .then(res => {
        this.careerStone = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.companyService()
      .retrieve()
      .then(res => {
        this.companies = res.data;
      });
    this.menteeService()
      .retrieve()
      .then(res => {
        this.mentees = res.data;
      });
  }
}
