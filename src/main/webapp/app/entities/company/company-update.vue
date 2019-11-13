<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="mymentorApp.company.home.createOrEditLabel" v-text="$t('mymentorApp.company.home.createOrEditLabel')">Create or edit a Company</h2>
                <div>
                    <div class="form-group" v-if="company.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="company.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.company.name')" for="company-name">Name</label>
                        <input type="text" class="form-control" name="name" id="company-name"
                            :class="{'valid': !$v.company.name.$invalid, 'invalid': $v.company.name.$invalid }" v-model="$v.company.name.$model" />
                        <div v-if="$v.company.name.$anyDirty && $v.company.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.company.name.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 127 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.company.description')" for="company-description">Description</label>
                        <input type="text" class="form-control" name="description" id="company-description"
                            :class="{'valid': !$v.company.description.$invalid, 'invalid': $v.company.description.$invalid }" v-model="$v.company.description.$model" />
                        <div v-if="$v.company.description.$anyDirty && $v.company.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.company.description.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 1023 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.company.url')" for="company-url">Url</label>
                        <input type="text" class="form-control" name="url" id="company-url"
                            :class="{'valid': !$v.company.url.$invalid, 'invalid': $v.company.url.$invalid }" v-model="$v.company.url.$model" />
                        <div v-if="$v.company.url.$anyDirty && $v.company.url.$invalid">
                            <small class="form-text text-danger" v-if="!$v.company.url.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 255 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.company.address')" for="company-address">Address</label>
                        <select class="form-control" id="company-address" name="address" v-model="company.addressId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="addressOption.id" v-for="addressOption in addresses" :key="addressOption.id">{{addressOption.city}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.company.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./company-update.component.ts">
</script>
