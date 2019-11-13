<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="address">
                <h2 class="jh-entity-heading"><span v-text="$t('mymentorApp.address.detail.title')">Address</span> {{address.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('mymentorApp.address.street')">Street</span>
                    </dt>
                    <dd>
                        <span>{{address.street}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.address.streetNumber')">Street Number</span>
                    </dt>
                    <dd>
                        <span>{{address.streetNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.address.secondLine')">Second Line</span>
                    </dt>
                    <dd>
                        <span>{{address.secondLine}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.address.postcode')">Postcode</span>
                    </dt>
                    <dd>
                        <span>{{address.postcode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.address.city')">City</span>
                    </dt>
                    <dd>
                        <span>{{address.city}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="address.id" :to="{name: 'AddressEdit', params: {addressId: address.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./address-details.component.ts">
</script>
