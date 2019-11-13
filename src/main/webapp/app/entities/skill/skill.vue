<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('mymentorApp.skill.home.title')" id="skill-heading">Skills</span>
            <router-link :to="{name: 'SkillCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-skill">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('mymentorApp.skill.home.createLabel')">
                    Create a new Skill
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
                            v-bind:placeholder="$t('mymentorApp.skill.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && skills && skills.length === 0">
            <span v-text="$t('mymentorApp.skill.home.notFound')">No skills found</span>
        </div>
        <div class="table-responsive" v-if="skills && skills.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('mymentorApp.skill.name')">Name</span></th>
                    <th><span v-text="$t('mymentorApp.skill.type')">Type</span></th>
                    <th><span v-text="$t('mymentorApp.skill.description')">Description</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="skill in skills"
                    :key="skill.id">
                    <td>
                        <router-link :to="{name: 'SkillView', params: {skillId: skill.id}}">{{skill.id}}</router-link>
                    </td>
                    <td>{{skill.name}}</td>
                    <td v-text="$t('mymentorApp.SkillType.' + skill.type)">{{skill.type}}</td>
                    <td>{{skill.description}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SkillView', params: {skillId: skill.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'SkillEdit', params: {skillId: skill.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(skill)"
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
            <span slot="modal-title"><span id="mymentorApp.skill.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-skill-heading" v-bind:title="$t('mymentorApp.skill.delete.question')">Are you sure you want to delete this Skill?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-skill" v-text="$t('entity.action.delete')" v-on:click="removeSkill()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./skill.component.ts">
</script>
