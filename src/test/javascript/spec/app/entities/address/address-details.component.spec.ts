/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AddressDetailComponent from '@/entities/address/address-details.vue';
import AddressClass from '@/entities/address/address-details.component';
import AddressService from '@/entities/address/address.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Address Management Detail Component', () => {
    let wrapper: Wrapper<AddressClass>;
    let comp: AddressClass;
    let addressServiceStub: SinonStubbedInstance<AddressService>;

    beforeEach(() => {
      addressServiceStub = sinon.createStubInstance<AddressService>(AddressService);

      wrapper = shallowMount<AddressClass>(AddressDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { addressService: () => addressServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAddress = { id: 123 };
        addressServiceStub.find.resolves(foundAddress);

        // WHEN
        comp.retrieveAddress(123);
        await comp.$nextTick();

        // THEN
        expect(comp.address).toBe(foundAddress);
      });
    });
  });
});
