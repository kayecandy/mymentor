import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMentee } from '@/shared/model/mentee.model';
import AlertService from '@/shared/alert/alert.service';

import MenteeService from './mentee.service';

@Component
export default class Mentee extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('menteeService') private menteeService: () => MenteeService;
  public currentSearch = '';
  private removeId: number = null;
  public mentees: IMentee[] = [];

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
    this.retrieveAllMentees();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllMentees();
  }

  public clear(): void {
    this.currentSearch = '';
    this.retrieveAllMentees();
  }

  public retrieveAllMentees(): void {
    this.isFetching = true;

    if (this.currentSearch) {
      this.menteeService()
        .search(this.currentSearch)
        .then(
          res => {
            this.mentees = res;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
      return;
    }
    this.menteeService()
      .retrieve()
      .then(
        res => {
          this.mentees = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IMentee): void {
    this.removeId = instance.id;
  }

  public removeMentee(): void {
    this.menteeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mymentorApp.mentee.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllMentees();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
