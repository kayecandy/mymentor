/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import EduStoneDetailComponent from '@/entities/edu-stone/edu-stone-details.vue';
import EduStoneClass from '@/entities/edu-stone/edu-stone-details.component';
import EduStoneService from '@/entities/edu-stone/edu-stone.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('EduStone Management Detail Component', () => {
    let wrapper: Wrapper<EduStoneClass>;
    let comp: EduStoneClass;
    let eduStoneServiceStub: SinonStubbedInstance<EduStoneService>;

    beforeEach(() => {
      eduStoneServiceStub = sinon.createStubInstance<EduStoneService>(EduStoneService);

      wrapper = shallowMount<EduStoneClass>(EduStoneDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { eduStoneService: () => eduStoneServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEduStone = { id: 123 };
        eduStoneServiceStub.find.resolves(foundEduStone);

        // WHEN
        comp.retrieveEduStone(123);
        await comp.$nextTick();

        // THEN
        expect(comp.eduStone).toBe(foundEduStone);
      });
    });
  });
});
