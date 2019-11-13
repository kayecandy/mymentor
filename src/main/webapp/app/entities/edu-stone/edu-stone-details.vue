<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="eduStone">
                <h2 class="jh-entity-heading"><span v-text="$t('mymentorApp.eduStone.detail.title')">EduStone</span> {{eduStone.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('mymentorApp.eduStone.degree')">Degree</span>
                    </dt>
                    <dd>
                        <span>{{eduStone.degree}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.eduStone.grade')">Grade</span>
                    </dt>
                    <dd>
                        <span>{{eduStone.grade}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.eduStone.fromDate')">From Date</span>
                    </dt>
                    <dd>
                        <span>{{eduStone.fromDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.eduStone.toDate')">To Date</span>
                    </dt>
                    <dd>
                        <span>{{eduStone.toDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.eduStone.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{eduStone.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.eduStone.activities')">Activities</span>
                    </dt>
                    <dd>
                        <span>{{eduStone.activities}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.eduStone.school')">School</span>
                    </dt>
                    <dd>
                        <div v-if="eduStone.schoolId">
                            <router-link :to="{name: 'EduInstitutionView', params: {schoolId: eduStone.schoolId}}">{{eduStone.schoolName}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.eduStone.mentee')">Mentee</span>
                    </dt>
                    <dd>
                        <div v-if="eduStone.menteeId">
                            <router-link :to="{name: 'MenteeView', params: {menteeId: eduStone.menteeId}}">{{eduStone.menteeId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="eduStone.id" :to="{name: 'EduStoneEdit', params: {eduStoneId: eduStone.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./edu-stone-details.component.ts">
</script>
