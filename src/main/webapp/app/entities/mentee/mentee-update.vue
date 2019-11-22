<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="mymentorApp.mentee.home.createOrEditLabel" v-text="$t('mymentorApp.mentee.home.createOrEditLabel')">Create or edit a Mentee</h2>
                <div>
                    <div class="form-group" v-if="mentee.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="mentee.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.mentee.mobilePhone')" for="mentee-mobilePhone">Mobile Phone</label>
                        <input type="text" class="form-control" name="mobilePhone" id="mentee-mobilePhone"
                            :class="{'valid': !$v.mentee.mobilePhone.$invalid, 'invalid': $v.mentee.mobilePhone.$invalid }" v-model="$v.mentee.mobilePhone.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.mentee.landline')" for="mentee-landline">Landline</label>
                        <input type="text" class="form-control" name="landline" id="mentee-landline"
                            :class="{'valid': !$v.mentee.landline.$invalid, 'invalid': $v.mentee.landline.$invalid }" v-model="$v.mentee.landline.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.mentee.profileVisibleInInternet')" for="mentee-profileVisibleInInternet">Profile Visible In Internet</label>
                        <input type="checkbox" class="form-check" name="profileVisibleInInternet" id="mentee-profileVisibleInInternet"
                            :class="{'valid': !$v.mentee.profileVisibleInInternet.$invalid, 'invalid': $v.mentee.profileVisibleInInternet.$invalid }" v-model="$v.mentee.profileVisibleInInternet.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.mentee.ownWebsiteUrl')" for="mentee-ownWebsiteUrl">Own Website Url</label>
                        <input type="text" class="form-control" name="ownWebsiteUrl" id="mentee-ownWebsiteUrl"
                            :class="{'valid': !$v.mentee.ownWebsiteUrl.$invalid, 'invalid': $v.mentee.ownWebsiteUrl.$invalid }" v-model="$v.mentee.ownWebsiteUrl.$model" />
                        <div v-if="$v.mentee.ownWebsiteUrl.$anyDirty && $v.mentee.ownWebsiteUrl.$invalid">
                            <small class="form-text text-danger" v-if="!$v.mentee.ownWebsiteUrl.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 255 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.mentee.xingProfileUrl')" for="mentee-xingProfileUrl">Xing Profile Url</label>
                        <input type="text" class="form-control" name="xingProfileUrl" id="mentee-xingProfileUrl"
                            :class="{'valid': !$v.mentee.xingProfileUrl.$invalid, 'invalid': $v.mentee.xingProfileUrl.$invalid }" v-model="$v.mentee.xingProfileUrl.$model" />
                        <div v-if="$v.mentee.xingProfileUrl.$anyDirty && $v.mentee.xingProfileUrl.$invalid">
                            <small class="form-text text-danger" v-if="!$v.mentee.xingProfileUrl.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 255 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.mentee.linkedInProfileUrl')" for="mentee-linkedInProfileUrl">Linked In Profile Url</label>
                        <input type="text" class="form-control" name="linkedInProfileUrl" id="mentee-linkedInProfileUrl"
                            :class="{'valid': !$v.mentee.linkedInProfileUrl.$invalid, 'invalid': $v.mentee.linkedInProfileUrl.$invalid }" v-model="$v.mentee.linkedInProfileUrl.$model" />
                        <div v-if="$v.mentee.linkedInProfileUrl.$anyDirty && $v.mentee.linkedInProfileUrl.$invalid">
                            <small class="form-text text-danger" v-if="!$v.mentee.linkedInProfileUrl.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 255 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('mymentorApp.mentee.user')" for="mentee-user">User</label>
                        <select class="form-control" id="mentee-user" name="user" v-model="mentee.userId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="userOption.id" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.mentee.address')" for="mentee-address">Address</label>}
                        <select class="form-control" id="mentee-address" name="address" v-model="mentee.addressId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="addressOption.id" v-for="addressOption in addresses" :key="addressOption.id">{{addressOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label v-text="$t('mymentorApp.mentee.possessedSkill')" for="mentee-possessedSkill">Possessed Skill</label>
                        <select class="form-control" id="mentee-possessedSkill" multiple name="possessedSkill" v-model="mentee.possessedSkills">
                            <option v-bind:value="getSelected(mentee.possessedSkills, possessedSkillOption)" v-for="possessedSkillOption in possessedSkills" :key="possessedSkillOption.id">{{possessedSkillOption.id}} {{possessedSkillOption.skillName}}</option>
                            }
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.mentee.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./mentee-update.component.ts">
</script>
