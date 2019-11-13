/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MenteeUpdateComponent from '@/entities/mentee/mentee-update.vue';
import MenteeClass from '@/entities/mentee/mentee-update.component';
import MenteeService from '@/entities/mentee/mentee.service';

import UserService from '@/admin/user-management/user-management.service';

import AddressService from '@/entities/address/address.service';

import CareerStoneService from '@/entities/career-stone/career-stone.service';

import EduStoneService from '@/entities/edu-stone/edu-stone.service';

import CertificateService from '@/entities/certificate/certificate.service';

import LanguageService from '@/entities/language/language.service';

import PossessedSkillService from '@/entities/possessed-skill/possessed-skill.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Mentee Management Update Component', () => {
    let wrapper: Wrapper<MenteeClass>;
    let comp: MenteeClass;
    let menteeServiceStub: SinonStubbedInstance<MenteeService>;

    beforeEach(() => {
      menteeServiceStub = sinon.createStubInstance<MenteeService>(MenteeService);

      wrapper = shallowMount<MenteeClass>(MenteeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          menteeService: () => menteeServiceStub,

          userService: () => new UserService(),

          addressService: () => new AddressService(),

          careerStoneService: () => new CareerStoneService(),

          eduStoneService: () => new EduStoneService(),

          certificateService: () => new CertificateService(),

          languageService: () => new LanguageService(),

          possessedSkillService: () => new PossessedSkillService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.mentee = entity;
        menteeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(menteeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.mentee = entity;
        menteeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(menteeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
