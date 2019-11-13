<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="certificate">
                <h2 class="jh-entity-heading"><span v-text="$t('mymentorApp.certificate.detail.title')">Certificate</span> {{certificate.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('mymentorApp.certificate.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{certificate.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.certificate.issueDate')">Issue Date</span>
                    </dt>
                    <dd>
                        <span>{{certificate.issueDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.certificate.expireDate')">Expire Date</span>
                    </dt>
                    <dd>
                        <span>{{certificate.expireDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.certificate.url')">Url</span>
                    </dt>
                    <dd>
                        <span>{{certificate.url}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.certificate.institution')">Institution</span>
                    </dt>
                    <dd>
                        <div v-if="certificate.institutionId">
                            <router-link :to="{name: 'EduInstitutionView', params: {institutionId: certificate.institutionId}}">{{certificate.institutionName}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('mymentorApp.certificate.mentee')">Mentee</span>
                    </dt>
                    <dd>
                        <div v-if="certificate.menteeId">
                            <router-link :to="{name: 'MenteeView', params: {menteeId: certificate.menteeId}}">{{certificate.menteeId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="certificate.id" :to="{name: 'CertificateEdit', params: {certificateId: certificate.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./certificate-details.component.ts">
</script>
