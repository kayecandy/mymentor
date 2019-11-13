/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CareerStoneDetailComponent from '@/entities/career-stone/career-stone-details.vue';
import CareerStoneClass from '@/entities/career-stone/career-stone-details.component';
import CareerStoneService from '@/entities/career-stone/career-stone.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CareerStone Management Detail Component', () => {
    let wrapper: Wrapper<CareerStoneClass>;
    let comp: CareerStoneClass;
    let careerStoneServiceStub: SinonStubbedInstance<CareerStoneService>;

    beforeEach(() => {
      careerStoneServiceStub = sinon.createStubInstance<CareerStoneService>(CareerStoneService);

      wrapper = shallowMount<CareerStoneClass>(CareerStoneDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { careerStoneService: () => careerStoneServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCareerStone = { id: 123 };
        careerStoneServiceStub.find.resolves(foundCareerStone);

        // WHEN
        comp.retrieveCareerStone(123);
        await comp.$nextTick();

        // THEN
        expect(comp.careerStone).toBe(foundCareerStone);
      });
    });
  });
});
