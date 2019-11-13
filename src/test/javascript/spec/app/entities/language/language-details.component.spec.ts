/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import LanguageDetailComponent from '@/entities/language/language-details.vue';
import LanguageClass from '@/entities/language/language-details.component';
import LanguageService from '@/entities/language/language.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Language Management Detail Component', () => {
    let wrapper: Wrapper<LanguageClass>;
    let comp: LanguageClass;
    let languageServiceStub: SinonStubbedInstance<LanguageService>;

    beforeEach(() => {
      languageServiceStub = sinon.createStubInstance<LanguageService>(LanguageService);

      wrapper = shallowMount<LanguageClass>(LanguageDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { languageService: () => languageServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLanguage = { id: 123 };
        languageServiceStub.find.resolves(foundLanguage);

        // WHEN
        comp.retrieveLanguage(123);
        await comp.$nextTick();

        // THEN
        expect(comp.language).toBe(foundLanguage);
      });
    });
  });
});
