<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('mymentorApp.eduStone.home.title')" id="edu-stone-heading">Edu Stones</span>
            <router-link :to="{name: 'EduStoneCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-edu-stone">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('mymentorApp.eduStone.home.createLabel')">
                    Create a new Edu Stone
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
                            v-bind:placeholder="$t('mymentorApp.eduStone.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && eduStones && eduStones.length === 0">
            <span v-text="$t('mymentorApp.eduStone.home.notFound')">No eduStones found</span>
        </div>
        <div class="table-responsive" v-if="eduStones && eduStones.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('mymentorApp.eduStone.degree')">Degree</span></th>
                    <th><span v-text="$t('mymentorApp.eduStone.grade')">Grade</span></th>
                    <th><span v-text="$t('mymentorApp.eduStone.fromDate')">From Date</span></th>
                    <th><span v-text="$t('mymentorApp.eduStone.toDate')">To Date</span></th>
                    <th><span v-text="$t('mymentorApp.eduStone.description')">Description</span></th>
                    <th><span v-text="$t('mymentorApp.eduStone.activities')">Activities</span></th>
                    <th><span v-text="$t('mymentorApp.eduStone.school')">School</span></th>
                    <th><span v-text="$t('mymentorApp.eduStone.mentee')">Mentee</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="eduStone in eduStones"
                    :key="eduStone.id">
                    <td>
                        <router-link :to="{name: 'EduStoneView', params: {eduStoneId: eduStone.id}}">{{eduStone.id}}</router-link>
                    </td>
                    <td>{{eduStone.degree}}</td>
                    <td>{{eduStone.grade}}</td>
                    <td>{{eduStone.fromDate}}</td>
                    <td>{{eduStone.toDate}}</td>
                    <td>{{eduStone.description}}</td>
                    <td>{{eduStone.activities}}</td>
                    <td>
                        <div v-if="eduStone.schoolId">
                            <router-link :to="{name: 'EduInstitutionView', params: {schoolId: eduStone.schoolId}}">{{eduStone.schoolName}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="eduStone.menteeId">
                            <router-link :to="{name: 'MenteeView', params: {menteeId: eduStone.menteeId}}">{{eduStone.menteeId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'EduStoneView', params: {eduStoneId: eduStone.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'EduStoneEdit', params: {eduStoneId: eduStone.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(eduStone)"
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
            <span slot="modal-title"><span id="mymentorApp.eduStone.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-eduStone-heading" v-bind:title="$t('mymentorApp.eduStone.delete.question')">Are you sure you want to delete this Edu Stone?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-eduStone" v-text="$t('entity.action.delete')" v-on:click="removeEduStone()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./edu-stone.component.ts">
</script>
