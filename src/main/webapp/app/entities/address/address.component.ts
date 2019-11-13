import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAddress } from '@/shared/model/address.model';
import AlertService from '@/shared/alert/alert.service';

import AddressService from './address.service';

@Component
export default class Address extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('addressService') private addressService: () => AddressService;
  private removeId: number = null;
  public addresses: IAddress[] = [];

  public isFetching = false;
  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public getAlertFromStore() {
    this.dismissCountDown = this.$store.getters.dismissCountDown;
    this.dismissSecs = this.$store.getters.dismissSecs;
    this.alertType = this.$store.getters.alertType;
    this.alertMessage = this.$store.getters.alertMessage;
  }

  public countDownChanged(dismissCountDown: number) {
    this.alertService().countDownChanged(dismissCountDown);
    this.getAlertFromStore();
  }

  public mounted(): void {
    this.retrieveAllAddresss();
  }

  public clear(): void {
    this.retrieveAllAddresss();
  }

  public retrieveAllAddresss(): void {
    this.isFetching = true;

    this.addressService()
      .retrieve()
      .then(
        res => {
          this.addresses = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAddress): void {
    this.removeId = instance.id;
  }

  public removeAddress(): void {
    this.addressService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mymentorApp.address.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllAddresss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
