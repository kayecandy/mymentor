import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICareerStone } from '@/shared/model/career-stone.model';
import AlertService from '@/shared/alert/alert.service';

import CareerStoneService from './career-stone.service';

@Component
export default class CareerStone extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('careerStoneService') private careerStoneService: () => CareerStoneService;
  public currentSearch = '';
  private removeId: number = null;
  public careerStones: ICareerStone[] = [];

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
    this.retrieveAllCareerStones();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllCareerStones();
  }

  public clear(): void {
    this.currentSearch = '';
    this.retrieveAllCareerStones();
  }

  public retrieveAllCareerStones(): void {
    this.isFetching = true;

    if (this.currentSearch) {
      this.careerStoneService()
        .search(this.currentSearch)
        .then(
          res => {
            this.careerStones = res;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
      return;
    }
    this.careerStoneService()
      .retrieve()
      .then(
        res => {
          this.careerStones = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICareerStone): void {
    this.removeId = instance.id;
  }

  public removeCareerStone(): void {
    this.careerStoneService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mymentorApp.careerStone.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllCareerStones();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
