<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('mymentorApp.careerStone.home.title')" id="career-stone-heading">Career Stones</span>
            <router-link :to="{name: 'CareerStoneCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-career-stone">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('mymentorApp.careerStone.home.createLabel')">
                    Create a new Career Stone
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
                            v-bind:placeholder="$t('mymentorApp.careerStone.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && careerStones && careerStones.length === 0">
            <span v-text="$t('mymentorApp.careerStone.home.notFound')">No careerStones found</span>
        </div>
        <div class="table-responsive" v-if="careerStones && careerStones.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('mymentorApp.careerStone.title')">Title</span></th>
                    <th><span v-text="$t('mymentorApp.careerStone.fromDate')">From Date</span></th>
                    <th><span v-text="$t('mymentorApp.careerStone.toDate')">To Date</span></th>
                    <th><span v-text="$t('mymentorApp.careerStone.stillWorkingHere')">Still Working Here</span></th>
                    <th><span v-text="$t('mymentorApp.careerStone.location')">Location</span></th>
                    <th><span v-text="$t('mymentorApp.careerStone.description')">Description</span></th>
                    <th><span v-text="$t('mymentorApp.careerStone.company')">Company</span></th>
                    <th><span v-text="$t('mymentorApp.careerStone.mentee')">Mentee</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="careerStone in careerStones"
                    :key="careerStone.id">
                    <td>
                        <router-link :to="{name: 'CareerStoneView', params: {careerStoneId: careerStone.id}}">{{careerStone.id}}</router-link>
                    </td>
                    <td>{{careerStone.title}}</td>
                    <td>{{careerStone.fromDate}}</td>
                    <td>{{careerStone.toDate}}</td>
                    <td>{{careerStone.stillWorkingHere}}</td>
                    <td>{{careerStone.location}}</td>
                    <td>{{careerStone.description}}</td>
                    <td>
                        <div v-if="careerStone.companyId">
                            <router-link :to="{name: 'CompanyView', params: {companyId: careerStone.companyId}}">{{careerStone.companyName}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="careerStone.menteeId">
                            <router-link :to="{name: 'MenteeView', params: {menteeId: careerStone.menteeId}}">{{careerStone.menteeId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CareerStoneView', params: {careerStoneId: careerStone.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CareerStoneEdit', params: {careerStoneId: careerStone.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(careerStone)"
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
            <span slot="modal-title"><span id="mymentorApp.careerStone.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-careerStone-heading" v-bind:title="$t('mymentorApp.careerStone.delete.question')">Are you sure you want to delete this Career Stone?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-careerStone" v-text="$t('entity.action.delete')" v-on:click="removeCareerStone()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./career-stone.component.ts">
</script>
