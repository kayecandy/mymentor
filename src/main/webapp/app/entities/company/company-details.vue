<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="company">
                <h2 class="jh-entity-heading"><span v-text="$t('mymentorApp.company.detail.title')">Company</span> {{company.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('mymentorApp.company.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{company.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.company.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{company.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.company.url')">Url</span>
                    </dt>
                    <dd>
                        <span>{{company.url}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.company.address')">Address</span>
                    </dt>
                    <dd>
                        <div v-if="company.addressId">
                            <router-link :to="{name: 'AddressView', params: {addressId: company.addressId}}">{{company.addressCity}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="company.id" :to="{name: 'CompanyEdit', params: {companyId: company.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./company-details.component.ts">
</script>
