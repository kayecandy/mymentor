import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPossessedSkill } from '@/shared/model/possessed-skill.model';
import AlertService from '@/shared/alert/alert.service';

import PossessedSkillService from './possessed-skill.service';

@Component
export default class PossessedSkill extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('possessedSkillService') private possessedSkillService: () => PossessedSkillService;
  public currentSearch = '';
  private removeId: number = null;
  public possessedSkills: IPossessedSkill[] = [];

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
    this.retrieveAllPossessedSkills();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllPossessedSkills();
  }

  public clear(): void {
    this.currentSearch = '';
    this.retrieveAllPossessedSkills();
  }

  public retrieveAllPossessedSkills(): void {
    this.isFetching = true;

    if (this.currentSearch) {
      this.possessedSkillService()
        .search(this.currentSearch)
        .then(
          res => {
            this.possessedSkills = res;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
      return;
    }
    this.possessedSkillService()
      .retrieve()
      .then(
        res => {
          this.possessedSkills = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPossessedSkill): void {
    this.removeId = instance.id;
  }

  public removePossessedSkill(): void {
    this.possessedSkillService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mymentorApp.possessedSkill.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllPossessedSkills();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
