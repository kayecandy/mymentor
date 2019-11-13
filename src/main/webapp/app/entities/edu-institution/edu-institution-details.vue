<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="eduInstitution">
                <h2 class="jh-entity-heading"><span v-text="$t('mymentorApp.eduInstitution.detail.title')">EduInstitution</span> {{eduInstitution.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('mymentorApp.eduInstitution.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{eduInstitution.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.eduInstitution.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{eduInstitution.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.eduInstitution.url')">Url</span>
                    </dt>
                    <dd>
                        <span>{{eduInstitution.url}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.eduInstitution.address')">Address</span>
                    </dt>
                    <dd>
                        <div v-if="eduInstitution.addressId">
                            <router-link :to="{name: 'AddressView', params: {addressId: eduInstitution.addressId}}">{{eduInstitution.addressCity}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="eduInstitution.id" :to="{name: 'EduInstitutionEdit', params: {eduInstitutionId: eduInstitution.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./edu-institution-details.component.ts">
</script>
