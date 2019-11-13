/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SkillDetailComponent from '@/entities/skill/skill-details.vue';
import SkillClass from '@/entities/skill/skill-details.component';
import SkillService from '@/entities/skill/skill.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Skill Management Detail Component', () => {
    let wrapper: Wrapper<SkillClass>;
    let comp: SkillClass;
    let skillServiceStub: SinonStubbedInstance<SkillService>;

    beforeEach(() => {
      skillServiceStub = sinon.createStubInstance<SkillService>(SkillService);

      wrapper = shallowMount<SkillClass>(SkillDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { skillService: () => skillServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSkill = { id: 123 };
        skillServiceStub.find.resolves(foundSkill);

        // WHEN
        comp.retrieveSkill(123);
        await comp.$nextTick();

        // THEN
        expect(comp.skill).toBe(foundSkill);
      });
    });
  });
});
