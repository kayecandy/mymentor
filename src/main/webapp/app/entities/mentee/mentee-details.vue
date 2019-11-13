<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="mentee">
                <h2 class="jh-entity-heading"><span v-text="$t('mymentorApp.mentee.detail.title')">Mentee</span> {{mentee.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('mymentorApp.mentee.mobilePhone')">Mobile Phone</span>
                    </dt>
                    <dd>
                        <span>{{mentee.mobilePhone}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.mentee.landline')">Landline</span>
                    </dt>
                    <dd>
                        <span>{{mentee.landline}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.mentee.profileVisibleInInternet')">Profile Visible In Internet</span>
                    </dt>
                    <dd>
                        <span>{{mentee.profileVisibleInInternet}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.mentee.ownWebsiteUrl')">Own Website Url</span>
                    </dt>
                    <dd>
                        <span>{{mentee.ownWebsiteUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.mentee.xingProfileUrl')">Xing Profile Url</span>
                    </dt>
                    <dd>
                        <span>{{mentee.xingProfileUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.mentee.linkedInProfileUrl')">Linked In Profile Url</span>
                    </dt>
                    <dd>
                        <span>{{mentee.linkedInProfileUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.mentee.user')">User</span>
                    </dt>
                    <dd>
                        {{mentee.userLogin}}
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.mentee.address')">Address</span>
                    </dt>
                    <dd>
                        <div v-if="mentee.addressId">
                            <router-link :to="{name: 'AddressView', params: {addressId: mentee.addressId}}">{{mentee.addressId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.mentee.possessedSkill')">Possessed Skill</span>
                    </dt>
                    <dd>
                        <span v-for="(possessedSkill, i) in mentee.possessedSkills" :key="possessedSkill.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'PossessedSkillView', params: {possessedSkillId: possessedSkill.id}}">{{possessedSkill.skill}}</router-link>
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="mentee.id" :to="{name: 'MenteeEdit', params: {menteeId: mentee.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./mentee-details.component.ts">
</script>
