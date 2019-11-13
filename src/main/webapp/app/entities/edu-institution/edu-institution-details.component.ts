import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEduInstitution } from '@/shared/model/edu-institution.model';
import EduInstitutionService from './edu-institution.service';

@Component
export default class EduInstitutionDetails extends Vue {
  @Inject('eduInstitutionService') private eduInstitutionService: () => EduInstitutionService;
  public eduInstitution: IEduInstitution = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.eduInstitutionId) {
        vm.retrieveEduInstitution(to.params.eduInstitutionId);
      }
    });
  }

  public retrieveEduInstitution(eduInstitutionId) {
    this.eduInstitutionService()
      .find(eduInstitutionId)
      .then(res => {
        this.eduInstitution = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
