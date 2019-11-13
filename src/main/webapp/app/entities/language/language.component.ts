import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ILanguage } from '@/shared/model/language.model';
import AlertService from '@/shared/alert/alert.service';

import LanguageService from './language.service';

@Component
export default class Language extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('languageService') private languageService: () => LanguageService;
  public currentSearch = '';
  private removeId: number = null;
  public languages: ILanguage[] = [];

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
    this.retrieveAllLanguages();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllLanguages();
  }

  public clear(): void {
    this.currentSearch = '';
    this.retrieveAllLanguages();
  }

  public retrieveAllLanguages(): void {
    this.isFetching = true;

    if (this.currentSearch) {
      this.languageService()
        .search(this.currentSearch)
        .then(
          res => {
            this.languages = res;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
      return;
    }
    this.languageService()
      .retrieve()
      .then(
        res => {
          this.languages = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ILanguage): void {
    this.removeId = instance.id;
  }

  public removeLanguage(): void {
    this.languageService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mymentorApp.language.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllLanguages();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
