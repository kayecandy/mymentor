<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="mymentorApp.eduStone.home.createOrEditLabel" v-text="$t('mymentorApp.eduStone.home.createOrEditLabel')">Create or edit a EduStone</h2>
                <div>
                    <div class="form-group" v-if="eduStone.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="eduStone.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.eduStone.degree')" for="edu-stone-degree">Degree</label>
                        <input type="text" class="form-control" name="degree" id="edu-stone-degree"
                            :class="{'valid': !$v.eduStone.degree.$invalid, 'invalid': $v.eduStone.degree.$invalid }" v-model="$v.eduStone.degree.$model"  required/>
                        <div v-if="$v.eduStone.degree.$anyDirty && $v.eduStone.degree.$invalid">
                            <small class="form-text text-danger" v-if="!$v.eduStone.degree.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.eduStone.degree.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 127 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.eduStone.grade')" for="edu-stone-grade">Grade</label>
                        <input type="text" class="form-control" name="grade" id="edu-stone-grade"
                            :class="{'valid': !$v.eduStone.grade.$invalid, 'invalid': $v.eduStone.grade.$invalid }" v-model="$v.eduStone.grade.$model" />
                        <div v-if="$v.eduStone.grade.$anyDirty && $v.eduStone.grade.$invalid">
                            <small class="form-text text-danger" v-if="!$v.eduStone.grade.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 31 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.eduStone.fromDate')" for="edu-stone-fromDate">From Date</label>
                        <div class="input-group">
                            <input id="edu-stone-fromDate" type="date" class="form-control" name="fromDate"  :class="{'valid': !$v.eduStone.fromDate.$invalid, 'invalid': $v.eduStone.fromDate.$invalid }"
                            v-model="$v.eduStone.fromDate.$model"  required />
                        </div>
                        <div v-if="$v.eduStone.fromDate.$anyDirty && $v.eduStone.fromDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.eduStone.fromDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.eduStone.toDate')" for="edu-stone-toDate">To Date</label>
                        <div class="input-group">
                            <input id="edu-stone-toDate" type="date" class="form-control" name="toDate"  :class="{'valid': !$v.eduStone.toDate.$invalid, 'invalid': $v.eduStone.toDate.$invalid }"
                            v-model="$v.eduStone.toDate.$model"  required />
                        </div>
                        <div v-if="$v.eduStone.toDate.$anyDirty && $v.eduStone.toDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.eduStone.toDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.eduStone.description')" for="edu-stone-description">Description</label>
                        <input type="text" class="form-control" name="description" id="edu-stone-description"
                            :class="{'valid': !$v.eduStone.description.$invalid, 'invalid': $v.eduStone.description.$invalid }" v-model="$v.eduStone.description.$model" />
                        <div v-if="$v.eduStone.description.$anyDirty && $v.eduStone.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.eduStone.description.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 1023 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.eduStone.activities')" for="edu-stone-activities">Activities</label>
                        <input type="text" class="form-control" name="activities" id="edu-stone-activities"
                            :class="{'valid': !$v.eduStone.activities.$invalid, 'invalid': $v.eduStone.activities.$invalid }" v-model="$v.eduStone.activities.$model" />
                        <div v-if="$v.eduStone.activities.$anyDirty && $v.eduStone.activities.$invalid">
                            <small class="form-text text-danger" v-if="!$v.eduStone.activities.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 1023 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('mymentorApp.eduStone.school')" for="edu-stone-school">School</label>
                        <select class="form-control" id="edu-stone-school" name="school" v-model="eduStone.schoolId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="eduInstitutionOption.id" v-for="eduInstitutionOption in eduInstitutions" :key="eduInstitutionOption.id">{{eduInstitutionOption.name}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('mymentorApp.eduStone.mentee')" for="edu-stone-mentee">Mentee</label>
                        <select class="form-control" id="edu-stone-mentee" name="mentee" v-model="eduStone.menteeId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="menteeOption.id" v-for="menteeOption in mentees" :key="menteeOption.id">{{menteeOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.eduStone.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./edu-stone-update.component.ts">
</script>
