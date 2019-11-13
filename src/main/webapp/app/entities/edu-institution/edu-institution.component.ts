import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEduInstitution } from '@/shared/model/edu-institution.model';
import AlertService from '@/shared/alert/alert.service';

import EduInstitutionService from './edu-institution.service';

@Component
export default class EduInstitution extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('eduInstitutionService') private eduInstitutionService: () => EduInstitutionService;
  public currentSearch = '';
  private removeId: number = null;
  public eduInstitutions: IEduInstitution[] = [];

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
    this.retrieveAllEduInstitutions();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllEduInstitutions();
  }

  public clear(): void {
    this.currentSearch = '';
    this.retrieveAllEduInstitutions();
  }

  public retrieveAllEduInstitutions(): void {
    this.isFetching = true;

    if (this.currentSearch) {
      this.eduInstitutionService()
        .search(this.currentSearch)
        .then(
          res => {
            this.eduInstitutions = res;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
      return;
    }
    this.eduInstitutionService()
      .retrieve()
      .then(
        res => {
          this.eduInstitutions = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IEduInstitution): void {
    this.removeId = instance.id;
  }

  public removeEduInstitution(): void {
    this.eduInstitutionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mymentorApp.eduInstitution.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllEduInstitutions();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
