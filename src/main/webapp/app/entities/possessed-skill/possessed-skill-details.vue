<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="possessedSkill">
                <h2 class="jh-entity-heading"><span v-text="$t('mymentorApp.possessedSkill.detail.title')">PossessedSkill</span> {{possessedSkill.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('mymentorApp.possessedSkill.topSkill')">Top Skill</span>
                    </dt>
                    <dd>
                        <span>{{possessedSkill.topSkill}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.possessedSkill.skill')">Skill</span>
                    </dt>
                    <dd>
                        <div v-if="possessedSkill.skillId">
                            <router-link :to="{name: 'SkillView', params: {skillId: possessedSkill.skillId}}">{{possessedSkill.skillName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="possessedSkill.id" :to="{name: 'PossessedSkillEdit', params: {possessedSkillId: possessedSkill.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./possessed-skill-details.component.ts">
</script>
