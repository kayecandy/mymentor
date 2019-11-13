import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISkill } from '@/shared/model/skill.model';
import AlertService from '@/shared/alert/alert.service';

import SkillService from './skill.service';

@Component
export default class Skill extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('skillService') private skillService: () => SkillService;
  public currentSearch = '';
  private removeId: number = null;
  public skills: ISkill[] = [];

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
    this.retrieveAllSkills();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllSkills();
  }

  public clear(): void {
    this.currentSearch = '';
    this.retrieveAllSkills();
  }

  public retrieveAllSkills(): void {
    this.isFetching = true;

    if (this.currentSearch) {
      this.skillService()
        .search(this.currentSearch)
        .then(
          res => {
            this.skills = res;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
      return;
    }
    this.skillService()
      .retrieve()
      .then(
        res => {
          this.skills = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ISkill): void {
    this.removeId = instance.id;
  }

  public removeSkill(): void {
    this.skillService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mymentorApp.skill.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllSkills();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
