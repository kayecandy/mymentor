/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EduStoneUpdateComponent from '@/entities/edu-stone/edu-stone-update.vue';
import EduStoneClass from '@/entities/edu-stone/edu-stone-update.component';
import EduStoneService from '@/entities/edu-stone/edu-stone.service';

import EduInstitutionService from '@/entities/edu-institution/edu-institution.service';

import MenteeService from '@/entities/mentee/mentee.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('EduStone Management Update Component', () => {
    let wrapper: Wrapper<EduStoneClass>;
    let comp: EduStoneClass;
    let eduStoneServiceStub: SinonStubbedInstance<EduStoneService>;

    beforeEach(() => {
      eduStoneServiceStub = sinon.createStubInstance<EduStoneService>(EduStoneService);

      wrapper = shallowMount<EduStoneClass>(EduStoneUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          eduStoneService: () => eduStoneServiceStub,

          eduInstitutionService: () => new EduInstitutionService(),

          menteeService: () => new MenteeService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.eduStone = entity;
        eduStoneServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(eduStoneServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.eduStone = entity;
        eduStoneServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(eduStoneServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
