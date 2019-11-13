/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CareerStoneUpdateComponent from '@/entities/career-stone/career-stone-update.vue';
import CareerStoneClass from '@/entities/career-stone/career-stone-update.component';
import CareerStoneService from '@/entities/career-stone/career-stone.service';

import CompanyService from '@/entities/company/company.service';

import MenteeService from '@/entities/mentee/mentee.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CareerStone Management Update Component', () => {
    let wrapper: Wrapper<CareerStoneClass>;
    let comp: CareerStoneClass;
    let careerStoneServiceStub: SinonStubbedInstance<CareerStoneService>;

    beforeEach(() => {
      careerStoneServiceStub = sinon.createStubInstance<CareerStoneService>(CareerStoneService);

      wrapper = shallowMount<CareerStoneClass>(CareerStoneUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          careerStoneService: () => careerStoneServiceStub,

          companyService: () => new CompanyService(),

          menteeService: () => new MenteeService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.careerStone = entity;
        careerStoneServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(careerStoneServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.careerStone = entity;
        careerStoneServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(careerStoneServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
