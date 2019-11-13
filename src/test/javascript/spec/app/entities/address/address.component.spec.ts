/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AddressComponent from '@/entities/address/address.vue';
import AddressClass from '@/entities/address/address.component';
import AddressService from '@/entities/address/address.service';

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
  describe('Address Management Component', () => {
    let wrapper: Wrapper<AddressClass>;
    let comp: AddressClass;
    let addressServiceStub: SinonStubbedInstance<AddressService>;

    beforeEach(() => {
      addressServiceStub = sinon.createStubInstance<AddressService>(AddressService);
      addressServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AddressClass>(AddressComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          addressService: () => addressServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      addressServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAddresss();
      await comp.$nextTick();

      // THEN
      expect(addressServiceStub.retrieve.called).toBeTruthy();
      expect(comp.addresses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      addressServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeAddress();
      await comp.$nextTick();

      // THEN
      expect(addressServiceStub.delete.called).toBeTruthy();
      expect(addressServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
