/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PossessedSkillDetailComponent from '@/entities/possessed-skill/possessed-skill-details.vue';
import PossessedSkillClass from '@/entities/possessed-skill/possessed-skill-details.component';
import PossessedSkillService from '@/entities/possessed-skill/possessed-skill.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PossessedSkill Management Detail Component', () => {
    let wrapper: Wrapper<PossessedSkillClass>;
    let comp: PossessedSkillClass;
    let possessedSkillServiceStub: SinonStubbedInstance<PossessedSkillService>;

    beforeEach(() => {
      possessedSkillServiceStub = sinon.createStubInstance<PossessedSkillService>(PossessedSkillService);

      wrapper = shallowMount<PossessedSkillClass>(PossessedSkillDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { possessedSkillService: () => possessedSkillServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPossessedSkill = { id: 123 };
        possessedSkillServiceStub.find.resolves(foundPossessedSkill);

        // WHEN
        comp.retrievePossessedSkill(123);
        await comp.$nextTick();

        // THEN
        expect(comp.possessedSkill).toBe(foundPossessedSkill);
      });
    });
  });
});
