<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="mymentorApp.careerStone.home.createOrEditLabel" v-text="$t('mymentorApp.careerStone.home.createOrEditLabel')">Create or edit a CareerStone</h2>
                <div>
                    <div class="form-group" v-if="careerStone.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="careerStone.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.careerStone.title')" for="career-stone-title">Title</label>
                        <input type="text" class="form-control" name="title" id="career-stone-title"
                            :class="{'valid': !$v.careerStone.title.$invalid, 'invalid': $v.careerStone.title.$invalid }" v-model="$v.careerStone.title.$model"  required/>
                        <div v-if="$v.careerStone.title.$anyDirty && $v.careerStone.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.careerStone.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.careerStone.title.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 127 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.careerStone.fromDate')" for="career-stone-fromDate">From Date</label>
                        <div class="input-group">
                            <input id="career-stone-fromDate" type="date" class="form-control" name="fromDate"  :class="{'valid': !$v.careerStone.fromDate.$invalid, 'invalid': $v.careerStone.fromDate.$invalid }"
                            v-model="$v.careerStone.fromDate.$model"  required />
                        </div>
                        <div v-if="$v.careerStone.fromDate.$anyDirty && $v.careerStone.fromDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.careerStone.fromDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.careerStone.toDate')" for="career-stone-toDate">To Date</label>
                        <div class="input-group">
                            <input id="career-stone-toDate" type="date" class="form-control" name="toDate"  :class="{'valid': !$v.careerStone.toDate.$invalid, 'invalid': $v.careerStone.toDate.$invalid }"
                            v-model="$v.careerStone.toDate.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.careerStone.stillWorkingHere')" for="career-stone-stillWorkingHere">Still Working Here</label>
                        <input type="checkbox" class="form-check" name="stillWorkingHere" id="career-stone-stillWorkingHere"
                            :class="{'valid': !$v.careerStone.stillWorkingHere.$invalid, 'invalid': $v.careerStone.stillWorkingHere.$invalid }" v-model="$v.careerStone.stillWorkingHere.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.careerStone.location')" for="career-stone-location">Location</label>
                        <input type="text" class="form-control" name="location" id="career-stone-location"
                            :class="{'valid': !$v.careerStone.location.$invalid, 'invalid': $v.careerStone.location.$invalid }" v-model="$v.careerStone.location.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.careerStone.description')" for="career-stone-description">Description</label>
                        <input type="text" class="form-control" name="description" id="career-stone-description"
                            :class="{'valid': !$v.careerStone.description.$invalid, 'invalid': $v.careerStone.description.$invalid }" v-model="$v.careerStone.description.$model" />
                        <div v-if="$v.careerStone.description.$anyDirty && $v.careerStone.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.careerStone.description.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 1023 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('mymentorApp.careerStone.company')" for="career-stone-company">Company</label>
                        <select class="form-control" id="career-stone-company" name="company" v-model="careerStone.companyId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="companyOption.id" v-for="companyOption in companies" :key="companyOption.id">{{companyOption.name}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('mymentorApp.careerStone.mentee')" for="career-stone-mentee">Mentee</label>
                        <select class="form-control" id="career-stone-mentee" name="mentee" v-model="careerStone.menteeId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="menteeOption.id" v-for="menteeOption in mentees" :key="menteeOption.id">{{menteeOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.careerStone.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./career-stone-update.component.ts">
</script>
