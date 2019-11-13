/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EduInstitutionComponent from '@/entities/edu-institution/edu-institution.vue';
import EduInstitutionClass from '@/entities/edu-institution/edu-institution.component';
import EduInstitutionService from '@/entities/edu-institution/edu-institution.service';

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
  describe('EduInstitution Management Component', () => {
    let wrapper: Wrapper<EduInstitutionClass>;
    let comp: EduInstitutionClass;
    let eduInstitutionServiceStub: SinonStubbedInstance<EduInstitutionService>;

    beforeEach(() => {
      eduInstitutionServiceStub = sinon.createStubInstance<EduInstitutionService>(EduInstitutionService);
      eduInstitutionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EduInstitutionClass>(EduInstitutionComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          eduInstitutionService: () => eduInstitutionServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      eduInstitutionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEduInstitutions();
      await comp.$nextTick();

      // THEN
      expect(eduInstitutionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.eduInstitutions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      eduInstitutionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeEduInstitution();
      await comp.$nextTick();

      // THEN
      expect(eduInstitutionServiceStub.delete.called).toBeTruthy();
      expect(eduInstitutionServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
