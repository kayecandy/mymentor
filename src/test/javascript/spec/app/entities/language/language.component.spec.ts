/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import LanguageComponent from '@/entities/language/language.vue';
import LanguageClass from '@/entities/language/language.component';
import LanguageService from '@/entities/language/language.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('Language Management Component', () => {
    let wrapper: Wrapper<LanguageClass>;
    let comp: LanguageClass;
    let languageServiceStub: SinonStubbedInstance<LanguageService>;

    beforeEach(() => {
      languageServiceStub = sinon.createStubInstance<LanguageService>(LanguageService);
      languageServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LanguageClass>(LanguageComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          languageService: () => languageServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      languageServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLanguages();
      await comp.$nextTick();

      // THEN
      expect(languageServiceStub.retrieve.called).toBeTruthy();
      expect(comp.languages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      languageServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeLanguage();
      await comp.$nextTick();

      // THEN
      expect(languageServiceStub.delete.called).toBeTruthy();
      expect(languageServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
