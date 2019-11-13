import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AddressService from '../address/address.service';
import { IAddress } from '@/shared/model/address.model';

import AlertService from '@/shared/alert/alert.service';
import { IEduInstitution, EduInstitution } from '@/shared/model/edu-institution.model';
import EduInstitutionService from './edu-institution.service';

const validations: any = {
  eduInstitution: {
    name: {
      maxLength: maxLength(127)
    },
    description: {
      maxLength: maxLength(1023)
    },
    url: {
      maxLength: maxLength(255)
    }
  }
};

@Component({
  validations
})
export default class EduInstitutionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('eduInstitutionService') private eduInstitutionService: () => EduInstitutionService;
  public eduInstitution: IEduInstitution = new EduInstitution();

  @Inject('addressService') private addressService: () => AddressService;

  public addresses: IAddress[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.eduInstitutionId) {
        vm.retrieveEduInstitution(to.params.eduInstitutionId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.eduInstitution.id) {
      this.eduInstitutionService()
        .update(this.eduInstitution)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.eduInstitution.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.eduInstitutionService()
        .create(this.eduInstitution)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.eduInstitution.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveEduInstitution(eduInstitutionId): void {
    this.eduInstitutionService()
      .find(eduInstitutionId)
      .then(res => {
        this.eduInstitution = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.addressService()
      .retrieve()
      .then(res => {
        this.addresses = res.data;
      });
  }
}
