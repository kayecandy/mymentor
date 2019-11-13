/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompanyComponent from '@/entities/company/company.vue';
import CompanyClass from '@/entities/company/company.component';
import CompanyService from '@/entities/company/company.service';

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
  describe('Company Management Component', () => {
    let wrapper: Wrapper<CompanyClass>;
    let comp: CompanyClass;
    let companyServiceStub: SinonStubbedInstance<CompanyService>;

    beforeEach(() => {
      companyServiceStub = sinon.createStubInstance<CompanyService>(CompanyService);
      companyServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CompanyClass>(CompanyComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          companyService: () => companyServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      companyServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCompanys();
      await comp.$nextTick();

      // THEN
      expect(companyServiceStub.retrieve.called).toBeTruthy();
      expect(comp.companies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      companyServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCompany();
      await comp.$nextTick();

      // THEN
      expect(companyServiceStub.delete.called).toBeTruthy();
      expect(companyServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
