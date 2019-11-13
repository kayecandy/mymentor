/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SkillComponent from '@/entities/skill/skill.vue';
import SkillClass from '@/entities/skill/skill.component';
import SkillService from '@/entities/skill/skill.service';

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
  describe('Skill Management Component', () => {
    let wrapper: Wrapper<SkillClass>;
    let comp: SkillClass;
    let skillServiceStub: SinonStubbedInstance<SkillService>;

    beforeEach(() => {
      skillServiceStub = sinon.createStubInstance<SkillService>(SkillService);
      skillServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SkillClass>(SkillComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          skillService: () => skillServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      skillServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSkills();
      await comp.$nextTick();

      // THEN
      expect(skillServiceStub.retrieve.called).toBeTruthy();
      expect(comp.skills[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      skillServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeSkill();
      await comp.$nextTick();

      // THEN
      expect(skillServiceStub.delete.called).toBeTruthy();
      expect(skillServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
