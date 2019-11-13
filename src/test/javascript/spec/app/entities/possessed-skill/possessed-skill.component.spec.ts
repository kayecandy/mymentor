/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PossessedSkillComponent from '@/entities/possessed-skill/possessed-skill.vue';
import PossessedSkillClass from '@/entities/possessed-skill/possessed-skill.component';
import PossessedSkillService from '@/entities/possessed-skill/possessed-skill.service';

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
  describe('PossessedSkill Management Component', () => {
    let wrapper: Wrapper<PossessedSkillClass>;
    let comp: PossessedSkillClass;
    let possessedSkillServiceStub: SinonStubbedInstance<PossessedSkillService>;

    beforeEach(() => {
      possessedSkillServiceStub = sinon.createStubInstance<PossessedSkillService>(PossessedSkillService);
      possessedSkillServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PossessedSkillClass>(PossessedSkillComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          possessedSkillService: () => possessedSkillServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      possessedSkillServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPossessedSkills();
      await comp.$nextTick();

      // THEN
      expect(possessedSkillServiceStub.retrieve.called).toBeTruthy();
      expect(comp.possessedSkills[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      possessedSkillServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePossessedSkill();
      await comp.$nextTick();

      // THEN
      expect(possessedSkillServiceStub.delete.called).toBeTruthy();
      expect(possessedSkillServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
