/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompanyUpdateComponent from '@/entities/company/company-update.vue';
import CompanyClass from '@/entities/company/company-update.component';
import CompanyService from '@/entities/company/company.service';

import AddressService from '@/entities/address/address.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Company Management Update Component', () => {
    let wrapper: Wrapper<CompanyClass>;
    let comp: CompanyClass;
    let companyServiceStub: SinonStubbedInstance<CompanyService>;

    beforeEach(() => {
      companyServiceStub = sinon.createStubInstance<CompanyService>(CompanyService);

      wrapper = shallowMount<CompanyClass>(CompanyUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          companyService: () => companyServiceStub,

          addressService: () => new AddressService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.company = entity;
        companyServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companyServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.company = entity;
        companyServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companyServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
