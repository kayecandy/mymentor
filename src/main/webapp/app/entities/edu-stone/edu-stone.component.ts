import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEduStone } from '@/shared/model/edu-stone.model';
import AlertService from '@/shared/alert/alert.service';

import EduStoneService from './edu-stone.service';

@Component
export default class EduStone extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('eduStoneService') private eduStoneService: () => EduStoneService;
  public currentSearch = '';
  private removeId: number = null;
  public eduStones: IEduStone[] = [];

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
    this.retrieveAllEduStones();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllEduStones();
  }

  public clear(): void {
    this.currentSearch = '';
    this.retrieveAllEduStones();
  }

  public retrieveAllEduStones(): void {
    this.isFetching = true;

    if (this.currentSearch) {
      this.eduStoneService()
        .search(this.currentSearch)
        .then(
          res => {
            this.eduStones = res;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
      return;
    }
    this.eduStoneService()
      .retrieve()
      .then(
        res => {
          this.eduStones = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IEduStone): void {
    this.removeId = instance.id;
  }

  public removeEduStone(): void {
    this.eduStoneService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mymentorApp.eduStone.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllEduStones();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
