<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="careerStone">
                <h2 class="jh-entity-heading"><span v-text="$t('mymentorApp.careerStone.detail.title')">CareerStone</span> {{careerStone.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('mymentorApp.careerStone.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{careerStone.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.careerStone.fromDate')">From Date</span>
                    </dt>
                    <dd>
                        <span>{{careerStone.fromDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.careerStone.toDate')">To Date</span>
                    </dt>
                    <dd>
                        <span>{{careerStone.toDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.careerStone.stillWorkingHere')">Still Working Here</span>
                    </dt>
                    <dd>
                        <span>{{careerStone.stillWorkingHere}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.careerStone.location')">Location</span>
                    </dt>
                    <dd>
                        <span>{{careerStone.location}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.careerStone.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{careerStone.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.careerStone.company')">Company</span>
                    </dt>
                    <dd>
                        <div v-if="careerStone.companyId">
                            <router-link :to="{name: 'CompanyView', params: {companyId: careerStone.companyId}}">{{careerStone.companyName}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.careerStone.mentee')">Mentee</span>
                    </dt>
                    <dd>
                        <div v-if="careerStone.menteeId">
                            <router-link :to="{name: 'MenteeView', params: {menteeId: careerStone.menteeId}}">{{careerStone.menteeId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="careerStone.id" :to="{name: 'CareerStoneEdit', params: {careerStoneId: careerStone.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./career-stone-details.component.ts">
</script>
