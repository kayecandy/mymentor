<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="language">
                <h2 class="jh-entity-heading"><span v-text="$t('mymentorApp.language.detail.title')">Language</span> {{language.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('mymentorApp.language.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{language.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.language.mentee')">Mentee</span>
                    </dt>
                    <dd>
                        <div v-if="language.menteeId">
                            <router-link :to="{name: 'MenteeView', params: {menteeId: language.menteeId}}">{{language.menteeId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="language.id" :to="{name: 'LanguageEdit', params: {languageId: language.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./language-details.component.ts">
</script>
