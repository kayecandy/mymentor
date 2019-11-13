/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EduStoneComponent from '@/entities/edu-stone/edu-stone.vue';
import EduStoneClass from '@/entities/edu-stone/edu-stone.component';
import EduStoneService from '@/entities/edu-stone/edu-stone.service';

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
  describe('EduStone Management Component', () => {
    let wrapper: Wrapper<EduStoneClass>;
    let comp: EduStoneClass;
    let eduStoneServiceStub: SinonStubbedInstance<EduStoneService>;

    beforeEach(() => {
      eduStoneServiceStub = sinon.createStubInstance<EduStoneService>(EduStoneService);
      eduStoneServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EduStoneClass>(EduStoneComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          eduStoneService: () => eduStoneServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      eduStoneServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEduStones();
      await comp.$nextTick();

      // THEN
      expect(eduStoneServiceStub.retrieve.called).toBeTruthy();
      expect(comp.eduStones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      eduStoneServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeEduStone();
      await comp.$nextTick();

      // THEN
      expect(eduStoneServiceStub.delete.called).toBeTruthy();
      expect(eduStoneServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
