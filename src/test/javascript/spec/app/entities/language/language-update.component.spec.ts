/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import LanguageUpdateComponent from '@/entities/language/language-update.vue';
import LanguageClass from '@/entities/language/language-update.component';
import LanguageService from '@/entities/language/language.service';

import MenteeService from '@/entities/mentee/mentee.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Language Management Update Component', () => {
    let wrapper: Wrapper<LanguageClass>;
    let comp: LanguageClass;
    let languageServiceStub: SinonStubbedInstance<LanguageService>;

    beforeEach(() => {
      languageServiceStub = sinon.createStubInstance<LanguageService>(LanguageService);

      wrapper = shallowMount<LanguageClass>(LanguageUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          languageService: () => languageServiceStub,

          menteeService: () => new MenteeService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.language = entity;
        languageServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(languageServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.language = entity;
        languageServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(languageServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
