<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('mymentorApp.company.home.title')" id="company-heading">Companies</span>
            <router-link :to="{name: 'CompanyCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-company">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('mymentorApp.company.home.createLabel')">
                    Create a new Company
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <div class="row">
            <div class="col-sm-12">
                <form name="searchForm" class="form-inline" v-on:submit.prevent="search(currentSearch)">
                    <div class="input-group w-100 mt-3">
                        <input type="text" class="form-control" name="currentSearch" id="currentSearch"
                            v-bind:placeholder="$t('mymentorApp.company.home.search')"
                            v-model="currentSearch" />
                        <button type="button" id="launch-search" class="btn btn-primary" v-on:click="search(currentSearch)">
                            <font-awesome-icon icon="search"></font-awesome-icon>
                        </button>
                        <button type="button" id="clear-search" class="btn btn-secondary" v-on:click="clear()"
                            v-if="currentSearch">
                            <font-awesome-icon icon="trash"></font-awesome-icon>
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && companies && companies.length === 0">
            <span v-text="$t('mymentorApp.company.home.notFound')">No companies found</span>
        </div>
        <div class="table-responsive" v-if="companies && companies.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('mymentorApp.company.name')">Name</span></th>
                    <th><span v-text="$t('mymentorApp.company.description')">Description</span></th>
                    <th><span v-text="$t('mymentorApp.company.url')">Url</span></th>
                    <th><span v-text="$t('mymentorApp.company.address')">Address</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="company in companies"
                    :key="company.id">
                    <td>
                        <router-link :to="{name: 'CompanyView', params: {companyId: company.id}}">{{company.id}}</router-link>
                    </td>
                    <td>{{company.name}}</td>
                    <td>{{company.description}}</td>
                    <td>{{company.url}}</td>
                    <td>
                        <div v-if="company.addressId">
                            <router-link :to="{name: 'AddressView', params: {addressId: company.addressId}}">{{company.addressCity}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CompanyView', params: {companyId: company.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompanyEdit', params: {companyId: company.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(company)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="mymentorApp.company.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-company-heading" v-bind:title="$t('mymentorApp.company.delete.question')">Are you sure you want to delete this Company?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-company" v-text="$t('entity.action.delete')" v-on:click="removeCompany()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./company.component.ts">
</script>
