import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICertificate } from '@/shared/model/certificate.model';
import AlertService from '@/shared/alert/alert.service';

import CertificateService from './certificate.service';

@Component
export default class Certificate extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('certificateService') private certificateService: () => CertificateService;
  private removeId: number = null;
  public certificates: ICertificate[] = [];

  public isFetching = false;
  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public getAlertFromStore() {
    this.dismissCountDown = this.$store.getters.dismissCountDown;
    this.dismissSecs = this.$store.getters.dismissSecs;
    this.alertType = this.$store.getters.alertType;
    this.alertMessage = this.$store.getters.alertMessage;
  }

  public countDownChanged(dismissCountDown: number) {
    this.alertService().countDownChanged(dismissCountDown);
    this.getAlertFromStore();
  }

  public mounted(): void {
    this.retrieveAllCertificates();
  }

  public clear(): void {
    this.retrieveAllCertificates();
  }

  public retrieveAllCertificates(): void {
    this.isFetching = true;

    this.certificateService()
      .retrieve()
      .then(
        res => {
          this.certificates = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICertificate): void {
    this.removeId = instance.id;
  }

  public removeCertificate(): void {
    this.certificateService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mymentorApp.certificate.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllCertificates();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
