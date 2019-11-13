<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="skill">
                <h2 class="jh-entity-heading"><span v-text="$t('mymentorApp.skill.detail.title')">Skill</span> {{skill.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('mymentorApp.skill.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{skill.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.skill.type')">Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('mymentorApp.SkillType.' + skill.type)">{{skill.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.skill.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{skill.description}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="skill.id" :to="{name: 'SkillEdit', params: {skillId: skill.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./skill-details.component.ts">
</script>
