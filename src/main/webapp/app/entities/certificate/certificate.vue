<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('mymentorApp.certificate.home.title')" id="certificate-heading">Certificates</span>
            <router-link :to="{name: 'CertificateCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-certificate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('mymentorApp.certificate.home.createLabel')">
                    Create a new Certificate
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && certificates && certificates.length === 0">
            <span v-text="$t('mymentorApp.certificate.home.notFound')">No certificates found</span>
        </div>
        <div class="table-responsive" v-if="certificates && certificates.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('mymentorApp.certificate.name')">Name</span></th>
                    <th><span v-text="$t('mymentorApp.certificate.issueDate')">Issue Date</span></th>
                    <th><span v-text="$t('mymentorApp.certificate.expireDate')">Expire Date</span></th>
                    <th><span v-text="$t('mymentorApp.certificate.url')">Url</span></th>
                    <th><span v-text="$t('mymentorApp.certificate.institution')">Institution</span></th>
                    <th><span v-text="$t('mymentorApp.certificate.mentee')">Mentee</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="certificate in certificates"
                    :key="certificate.id">
                    <td>
                        <router-link :to="{name: 'CertificateView', params: {certificateId: certificate.id}}">{{certificate.id}}</router-link>
                    </td>
                    <td>{{certificate.name}}</td>
                    <td>{{certificate.issueDate}}</td>
                    <td>{{certificate.expireDate}}</td>
                    <td>{{certificate.url}}</td>
                    <td>
                        <div v-if="certificate.institutionId">
                            <router-link :to="{name: 'EduInstitutionView', params: {institutionId: certificate.institutionId}}">{{certificate.institutionName}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="certificate.menteeId">
                            <router-link :to="{name: 'MenteeView', params: {menteeId: certificate.menteeId}}">{{certificate.menteeId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CertificateView', params: {certificateId: certificate.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CertificateEdit', params: {certificateId: certificate.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(certificate)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="mymentorApp.certificate.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-certificate-heading" v-bind:title="$t('mymentorApp.certificate.delete.question')">Are you sure you want to delete this Certificate?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-certificate" v-text="$t('entity.action.delete')" v-on:click="removeCertificate()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./certificate.component.ts">
</script>
