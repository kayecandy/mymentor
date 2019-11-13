<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="mymentorApp.certificate.home.createOrEditLabel" v-text="$t('mymentorApp.certificate.home.createOrEditLabel')">Create or edit a Certificate</h2>
                <div>
                    <div class="form-group" v-if="certificate.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="certificate.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.certificate.name')" for="certificate-name">Name</label>
                        <input type="text" class="form-control" name="name" id="certificate-name"
                            :class="{'valid': !$v.certificate.name.$invalid, 'invalid': $v.certificate.name.$invalid }" v-model="$v.certificate.name.$model"  required/>
                        <div v-if="$v.certificate.name.$anyDirty && $v.certificate.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.certificate.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.certificate.name.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 127 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.certificate.issueDate')" for="certificate-issueDate">Issue Date</label>
                        <div class="input-group">
                            <input id="certificate-issueDate" type="date" class="form-control" name="issueDate"  :class="{'valid': !$v.certificate.issueDate.$invalid, 'invalid': $v.certificate.issueDate.$invalid }"
                            v-model="$v.certificate.issueDate.$model"  required />
                        </div>
                        <div v-if="$v.certificate.issueDate.$anyDirty && $v.certificate.issueDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.certificate.issueDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.certificate.expireDate')" for="certificate-expireDate">Expire Date</label>
                        <div class="input-group">
                            <input id="certificate-expireDate" type="date" class="form-control" name="expireDate"  :class="{'valid': !$v.certificate.expireDate.$invalid, 'invalid': $v.certificate.expireDate.$invalid }"
                            v-model="$v.certificate.expireDate.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.certificate.url')" for="certificate-url">Url</label>
                        <input type="text" class="form-control" name="url" id="certificate-url"
                            :class="{'valid': !$v.certificate.url.$invalid, 'invalid': $v.certificate.url.$invalid }" v-model="$v.certificate.url.$model" />
                        <div v-if="$v.certificate.url.$anyDirty && $v.certificate.url.$invalid">
                            <small class="form-text text-danger" v-if="!$v.certificate.url.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 255 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('mymentorApp.certificate.institution')" for="certificate-institution">Institution</label>
                        <select class="form-control" id="certificate-institution" name="institution" v-model="$v.certificate.institutionId.$model" required>
                            <option v-if="!certificate.institutionId" v-bind:value="null" selected></option>
                            <option v-bind:value="eduInstitutionOption.id" v-for="eduInstitutionOption in eduInstitutions" :key="eduInstitutionOption.id">{{eduInstitutionOption.name}}</option>
                        </select>
                    </div>
                    <div v-if="$v.certificate.institutionId.$anyDirty && $v.certificate.institutionId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.certificate.institutionId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('mymentorApp.certificate.mentee')" for="certificate-mentee">Mentee</label>
                        <select class="form-control" id="certificate-mentee" name="mentee" v-model="certificate.menteeId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="menteeOption.id" v-for="menteeOption in mentees" :key="menteeOption.id">{{menteeOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.certificate.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./certificate-update.component.ts">
</script>
