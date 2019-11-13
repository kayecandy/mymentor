import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import EduInstitutionService from '../edu-institution/edu-institution.service';
import { IEduInstitution } from '@/shared/model/edu-institution.model';

import MenteeService from '../mentee/mentee.service';
import { IMentee } from '@/shared/model/mentee.model';

import AlertService from '@/shared/alert/alert.service';
import { ICertificate, Certificate } from '@/shared/model/certificate.model';
import CertificateService from './certificate.service';

const validations: any = {
  certificate: {
    name: {
      required,
      maxLength: maxLength(127)
    },
    issueDate: {
      required
    },
    expireDate: {},
    url: {
      maxLength: maxLength(255)
    },
    institutionId: {
      required
    }
  }
};

@Component({
  validations
})
export default class CertificateUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('certificateService') private certificateService: () => CertificateService;
  public certificate: ICertificate = new Certificate();

  @Inject('eduInstitutionService') private eduInstitutionService: () => EduInstitutionService;

  public eduInstitutions: IEduInstitution[] = [];

  @Inject('menteeService') private menteeService: () => MenteeService;

  public mentees: IMentee[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.certificateId) {
        vm.retrieveCertificate(to.params.certificateId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.certificate.id) {
      this.certificateService()
        .update(this.certificate)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.certificate.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.certificateService()
        .create(this.certificate)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.certificate.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCertificate(certificateId): void {
    this.certificateService()
      .find(certificateId)
      .then(res => {
        this.certificate = res;
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
