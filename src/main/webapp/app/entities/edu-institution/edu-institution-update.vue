<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="mymentorApp.eduInstitution.home.createOrEditLabel" v-text="$t('mymentorApp.eduInstitution.home.createOrEditLabel')">Create or edit a EduInstitution</h2>
                <div>
                    <div class="form-group" v-if="eduInstitution.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="eduInstitution.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.eduInstitution.name')" for="edu-institution-name">Name</label>
                        <input type="text" class="form-control" name="name" id="edu-institution-name"
                            :class="{'valid': !$v.eduInstitution.name.$invalid, 'invalid': $v.eduInstitution.name.$invalid }" v-model="$v.eduInstitution.name.$model" />
                        <div v-if="$v.eduInstitution.name.$anyDirty && $v.eduInstitution.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.eduInstitution.name.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 127 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.eduInstitution.description')" for="edu-institution-description">Description</label>
                        <input type="text" class="form-control" name="description" id="edu-institution-description"
                            :class="{'valid': !$v.eduInstitution.description.$invalid, 'invalid': $v.eduInstitution.description.$invalid }" v-model="$v.eduInstitution.description.$model" />
                        <div v-if="$v.eduInstitution.description.$anyDirty && $v.eduInstitution.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.eduInstitution.description.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 1023 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.eduInstitution.url')" for="edu-institution-url">Url</label>
                        <input type="text" class="form-control" name="url" id="edu-institution-url"
                            :class="{'valid': !$v.eduInstitution.url.$invalid, 'invalid': $v.eduInstitution.url.$invalid }" v-model="$v.eduInstitution.url.$model" />
                        <div v-if="$v.eduInstitution.url.$anyDirty && $v.eduInstitution.url.$invalid">
                            <small class="form-text text-danger" v-if="!$v.eduInstitution.url.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 255 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.eduInstitution.address')" for="edu-institution-address">Address</label>
                        <select class="form-control" id="edu-institution-address" name="address" v-model="eduInstitution.addressId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="addressOption.id" v-for="addressOption in addresses" :key="addressOption.id">{{addressOption.city}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.eduInstitution.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./edu-institution-update.component.ts">
</script>
