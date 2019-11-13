<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('mymentorApp.mentee.home.title')" id="mentee-heading">Mentees</span>
            <router-link :to="{name: 'MenteeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-mentee">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('mymentorApp.mentee.home.createLabel')">
                    Create a new Mentee
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
                            v-bind:placeholder="$t('mymentorApp.mentee.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && mentees && mentees.length === 0">
            <span v-text="$t('mymentorApp.mentee.home.notFound')">No mentees found</span>
        </div>
        <div class="table-responsive" v-if="mentees && mentees.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('mymentorApp.mentee.mobilePhone')">Mobile Phone</span></th>
                    <th><span v-text="$t('mymentorApp.mentee.landline')">Landline</span></th>
                    <th><span v-text="$t('mymentorApp.mentee.profileVisibleInInternet')">Profile Visible In Internet</span></th>
                    <th><span v-text="$t('mymentorApp.mentee.ownWebsiteUrl')">Own Website Url</span></th>
                    <th><span v-text="$t('mymentorApp.mentee.xingProfileUrl')">Xing Profile Url</span></th>
                    <th><span v-text="$t('mymentorApp.mentee.linkedInProfileUrl')">Linked In Profile Url</span></th>
                    <th><span v-text="$t('mymentorApp.mentee.user')">User</span></th>
                    <th><span v-text="$t('mymentorApp.mentee.address')">Address</span></th>
                    <th><span v-text="$t('mymentorApp.mentee.possessedSkill')">Possessed Skill</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="mentee in mentees"
                    :key="mentee.id">
                    <td>
                        <router-link :to="{name: 'MenteeView', params: {menteeId: mentee.id}}">{{mentee.id}}</router-link>
                    </td>
                    <td>{{mentee.mobilePhone}}</td>
                    <td>{{mentee.landline}}</td>
                    <td>{{mentee.profileVisibleInInternet}}</td>
                    <td>{{mentee.ownWebsiteUrl}}</td>
                    <td>{{mentee.xingProfileUrl}}</td>
                    <td>{{mentee.linkedInProfileUrl}}</td>
                    <td>
                        {{mentee.userLogin}}
                    </td>
                    <td>
                        <div v-if="mentee.addressId">
                            <router-link :to="{name: 'AddressView', params: {addressId: mentee.addressId}}">{{mentee.addressId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <span v-for="(possessedSkill, i) in mentee.possessedSkills" :key="possessedSkill.id">{{i > 0 ? ', ' : ''}}
                            <router-link class="form-control-static" :to="{name: 'PossessedSkillView', params: {possessedSkillId: possessedSkill.id}}">{{possessedSkill.skill}}</router-link>
                        </span>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MenteeView', params: {menteeId: mentee.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MenteeEdit', params: {menteeId: mentee.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(mentee)"
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
            <span slot="modal-title"><span id="mymentorApp.mentee.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-mentee-heading" v-bind:title="$t('mymentorApp.mentee.delete.question')">Are you sure you want to delete this Mentee?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-mentee" v-text="$t('entity.action.delete')" v-on:click="removeMentee()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./mentee.component.ts">
</script>
