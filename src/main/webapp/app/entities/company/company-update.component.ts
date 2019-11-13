import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AddressService from '../address/address.service';
import { IAddress } from '@/shared/model/address.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompany, Company } from '@/shared/model/company.model';
import CompanyService from './company.service';

const validations: any = {
  company: {
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
export default class CompanyUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('companyService') private companyService: () => CompanyService;
  public company: ICompany = new Company();

  @Inject('addressService') private addressService: () => AddressService;

  public addresses: IAddress[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyId) {
        vm.retrieveCompany(to.params.companyId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.company.id) {
      this.companyService()
        .update(this.company)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.company.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.companyService()
        .create(this.company)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.company.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCompany(companyId): void {
    this.companyService()
      .find(companyId)
      .then(res => {
        this.company = res;
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
