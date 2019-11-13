import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import MenteeService from '../mentee/mentee.service';
import { IMentee } from '@/shared/model/mentee.model';

import AlertService from '@/shared/alert/alert.service';
import { ILanguage, Language } from '@/shared/model/language.model';
import LanguageService from './language.service';

const validations: any = {
  language: {
    name: {
      required,
      maxLength: maxLength(127)
    }
  }
};

@Component({
  validations
})
export default class LanguageUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('languageService') private languageService: () => LanguageService;
  public language: ILanguage = new Language();

  @Inject('menteeService') private menteeService: () => MenteeService;

  public mentees: IMentee[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.languageId) {
        vm.retrieveLanguage(to.params.languageId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.language.id) {
      this.languageService()
        .update(this.language)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.language.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.languageService()
        .create(this.language)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mymentorApp.language.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveLanguage(languageId): void {
    this.languageService()
      .find(languageId)
      .then(res => {
        this.language = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.menteeService()
      .retrieve()
      .then(res => {
        this.mentees = res.data;
      });
  }
}
