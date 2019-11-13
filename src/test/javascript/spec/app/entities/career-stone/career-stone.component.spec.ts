/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CareerStoneComponent from '@/entities/career-stone/career-stone.vue';
import CareerStoneClass from '@/entities/career-stone/career-stone.component';
import CareerStoneService from '@/entities/career-stone/career-stone.service';

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
  describe('CareerStone Management Component', () => {
    let wrapper: Wrapper<CareerStoneClass>;
    let comp: CareerStoneClass;
    let careerStoneServiceStub: SinonStubbedInstance<CareerStoneService>;

    beforeEach(() => {
      careerStoneServiceStub = sinon.createStubInstance<CareerStoneService>(CareerStoneService);
      careerStoneServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CareerStoneClass>(CareerStoneComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          careerStoneService: () => careerStoneServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      careerStoneServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCareerStones();
      await comp.$nextTick();

      // THEN
      expect(careerStoneServiceStub.retrieve.called).toBeTruthy();
      expect(comp.careerStones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      careerStoneServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCareerStone();
      await comp.$nextTick();

      // THEN
      expect(careerStoneServiceStub.delete.called).toBeTruthy();
      expect(careerStoneServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
