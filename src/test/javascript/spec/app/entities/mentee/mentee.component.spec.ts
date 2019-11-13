/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MenteeComponent from '@/entities/mentee/mentee.vue';
import MenteeClass from '@/entities/mentee/mentee.component';
import MenteeService from '@/entities/mentee/mentee.service';

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
  describe('Mentee Management Component', () => {
    let wrapper: Wrapper<MenteeClass>;
    let comp: MenteeClass;
    let menteeServiceStub: SinonStubbedInstance<MenteeService>;

    beforeEach(() => {
      menteeServiceStub = sinon.createStubInstance<MenteeService>(MenteeService);
      menteeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MenteeClass>(MenteeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          menteeService: () => menteeServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      menteeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMentees();
      await comp.$nextTick();

      // THEN
      expect(menteeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.mentees[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      menteeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeMentee();
      await comp.$nextTick();

      // THEN
      expect(menteeServiceStub.delete.called).toBeTruthy();
      expect(menteeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
