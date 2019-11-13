/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import EduInstitutionDetailComponent from '@/entities/edu-institution/edu-institution-details.vue';
import EduInstitutionClass from '@/entities/edu-institution/edu-institution-details.component';
import EduInstitutionService from '@/entities/edu-institution/edu-institution.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('EduInstitution Management Detail Component', () => {
    let wrapper: Wrapper<EduInstitutionClass>;
    let comp: EduInstitutionClass;
    let eduInstitutionServiceStub: SinonStubbedInstance<EduInstitutionService>;

    beforeEach(() => {
      eduInstitutionServiceStub = sinon.createStubInstance<EduInstitutionService>(EduInstitutionService);

      wrapper = shallowMount<EduInstitutionClass>(EduInstitutionDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { eduInstitutionService: () => eduInstitutionServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEduInstitution = { id: 123 };
        eduInstitutionServiceStub.find.resolves(foundEduInstitution);

        // WHEN
        comp.retrieveEduInstitution(123);
        await comp.$nextTick();

        // THEN
        expect(comp.eduInstitution).toBe(foundEduInstitution);
      });
    });
  });
});
