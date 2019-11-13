<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="mymentorApp.skill.home.createOrEditLabel" v-text="$t('mymentorApp.skill.home.createOrEditLabel')">Create or edit a Skill</h2>
                <div>
                    <div class="form-group" v-if="skill.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="skill.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.skill.name')" for="skill-name">Name</label>
                        <input type="text" class="form-control" name="name" id="skill-name"
                            :class="{'valid': !$v.skill.name.$invalid, 'invalid': $v.skill.name.$invalid }" v-model="$v.skill.name.$model"  required/>
                        <div v-if="$v.skill.name.$anyDirty && $v.skill.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.skill.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.skill.name.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 127 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.skill.type')" for="skill-type">Type</label>
                        <select class="form-control" name="type" :class="{'valid': !$v.skill.type.$invalid, 'invalid': $v.skill.type.$invalid }" v-model="$v.skill.type.$model" id="skill-type"  required>
                            <option value="INDUSTRY_KNOWLEDGE" v-bind:label="$t('mymentorApp.SkillType.INDUSTRY_KNOWLEDGE')">INDUSTRY_KNOWLEDGE</option>
                            <option value="TOOLS_TECHNOLOGIES" v-bind:label="$t('mymentorApp.SkillType.TOOLS_TECHNOLOGIES')">TOOLS_TECHNOLOGIES</option>
                            <option value="INTERPERSONAL" v-bind:label="$t('mymentorApp.SkillType.INTERPERSONAL')">INTERPERSONAL</option>
                            <option value="OTHER" v-bind:label="$t('mymentorApp.SkillType.OTHER')">OTHER</option>
                        </select>
                        <div v-if="$v.skill.type.$anyDirty && $v.skill.type.$invalid">
                            <small class="form-text text-danger" v-if="!$v.skill.type.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.skill.description')" for="skill-description">Description</label>
                        <input type="text" class="form-control" name="description" id="skill-description"
                            :class="{'valid': !$v.skill.description.$invalid, 'invalid': $v.skill.description.$invalid }" v-model="$v.skill.description.$model" />
                        <div v-if="$v.skill.description.$anyDirty && $v.skill.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.skill.description.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 1023 characters.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.skill.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./skill-update.component.ts">
</script>
