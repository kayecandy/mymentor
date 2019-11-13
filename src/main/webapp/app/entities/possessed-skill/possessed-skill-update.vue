<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="mymentorApp.possessedSkill.home.createOrEditLabel" v-text="$t('mymentorApp.possessedSkill.home.createOrEditLabel')">Create or edit a PossessedSkill</h2>
                <div>
                    <div class="form-group" v-if="possessedSkill.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="possessedSkill.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.possessedSkill.topSkill')" for="possessed-skill-topSkill">Top Skill</label>
                        <input type="checkbox" class="form-check" name="topSkill" id="possessed-skill-topSkill"
                            :class="{'valid': !$v.possessedSkill.topSkill.$invalid, 'invalid': $v.possessedSkill.topSkill.$invalid }" v-model="$v.possessedSkill.topSkill.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('mymentorApp.possessedSkill.skill')" for="possessed-skill-skill">Skill</label>
                        <select class="form-control" id="possessed-skill-skill" name="skill" v-model="possessedSkill.skillId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="skillOption.id" v-for="skillOption in skills" :key="skillOption.id">{{skillOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.possessedSkill.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./possessed-skill-update.component.ts">
</script>
