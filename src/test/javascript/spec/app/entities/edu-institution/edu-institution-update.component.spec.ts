/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EduInstitutionUpdateComponent from '@/entities/edu-institution/edu-institution-update.vue';
import EduInstitutionClass from '@/entities/edu-institution/edu-institution-update.component';
import EduInstitutionService from '@/entities/edu-institution/edu-institution.service';

import AddressService from '@/entities/address/address.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('EduInstitution Management Update Component', () => {
    let wrapper: Wrapper<EduInstitutionClass>;
    let comp: EduInstitutionClass;
    let eduInstitutionServiceStub: SinonStubbedInstance<EduInstitutionService>;

    beforeEach(() => {
      eduInstitutionServiceStub = sinon.createStubInstance<EduInstitutionService>(EduInstitutionService);

      wrapper = shallowMount<EduInstitutionClass>(EduInstitutionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          eduInstitutionService: () => eduInstitutionServiceStub,

          addressService: () => new AddressService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.eduInstitution = entity;
        eduInstitutionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(eduInstitutionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.eduInstitution = entity;
        eduInstitutionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(eduInstitutionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
