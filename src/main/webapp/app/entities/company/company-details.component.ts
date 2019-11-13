import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompany } from '@/shared/model/company.model';
import CompanyService from './company.service';

@Component
export default class CompanyDetails extends Vue {
  @Inject('companyService') private companyService: () => CompanyService;
  public company: ICompany = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyId) {
        vm.retrieveCompany(to.params.companyId);
      }
    });
  }

  public retrieveCompany(companyId) {
    this.companyService()
      .find(companyId)
      .then(res => {
        this.company = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
