/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MenteeDetailComponent from '@/entities/mentee/mentee-details.vue';
import MenteeClass from '@/entities/mentee/mentee-details.component';
import MenteeService from '@/entities/mentee/mentee.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Mentee Management Detail Component', () => {
    let wrapper: Wrapper<MenteeClass>;
    let comp: MenteeClass;
    let menteeServiceStub: SinonStubbedInstance<MenteeService>;

    beforeEach(() => {
      menteeServiceStub = sinon.createStubInstance<MenteeService>(MenteeService);

      wrapper = shallowMount<MenteeClass>(MenteeDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { menteeService: () => menteeServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMentee = { id: 123 };
        menteeServiceStub.find.resolves(foundMentee);

        // WHEN
        comp.retrieveMentee(123);
        await comp.$nextTick();

        // THEN
        expect(comp.mentee).toBe(foundMentee);
      });
    });
  });
});
