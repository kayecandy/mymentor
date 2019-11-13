<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="mymentorApp.language.home.createOrEditLabel" v-text="$t('mymentorApp.language.home.createOrEditLabel')">Create or edit a Language</h2>
                <div>
                    <div class="form-group" v-if="language.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="language.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.language.name')" for="language-name">Name</label>
                        <input type="text" class="form-control" name="name" id="language-name"
                            :class="{'valid': !$v.language.name.$invalid, 'invalid': $v.language.name.$invalid }" v-model="$v.language.name.$model"  required/>
                        <div v-if="$v.language.name.$anyDirty && $v.language.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.language.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.language.name.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 127 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('mymentorApp.language.mentee')" for="language-mentee">Mentee</label>
                        <select class="form-control" id="language-mentee" name="mentee" v-model="language.menteeId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="menteeOption.id" v-for="menteeOption in mentees" :key="menteeOption.id">{{menteeOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.language.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./language-update.component.ts">
</script>
