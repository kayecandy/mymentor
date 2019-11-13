<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="mymentorApp.address.home.createOrEditLabel" v-text="$t('mymentorApp.address.home.createOrEditLabel')">Create or edit a Address</h2>
                <div>
                    <div class="form-group" v-if="address.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="address.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.address.street')" for="address-street">Street</label>
                        <input type="text" class="form-control" name="street" id="address-street"
                            :class="{'valid': !$v.address.street.$invalid, 'invalid': $v.address.street.$invalid }" v-model="$v.address.street.$model"  required/>
                        <div v-if="$v.address.street.$anyDirty && $v.address.street.$invalid">
                            <small class="form-text text-danger" v-if="!$v.address.street.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.address.street.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 127 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.address.streetNumber')" for="address-streetNumber">Street Number</label>
                        <input type="text" class="form-control" name="streetNumber" id="address-streetNumber"
                            :class="{'valid': !$v.address.streetNumber.$invalid, 'invalid': $v.address.streetNumber.$invalid }" v-model="$v.address.streetNumber.$model"  required/>
                        <div v-if="$v.address.streetNumber.$anyDirty && $v.address.streetNumber.$invalid">
                            <small class="form-text text-danger" v-if="!$v.address.streetNumber.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.address.streetNumber.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 7 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.address.secondLine')" for="address-secondLine">Second Line</label>
                        <input type="text" class="form-control" name="secondLine" id="address-secondLine"
                            :class="{'valid': !$v.address.secondLine.$invalid, 'invalid': $v.address.secondLine.$invalid }" v-model="$v.address.secondLine.$model" />
                        <div v-if="$v.address.secondLine.$anyDirty && $v.address.secondLine.$invalid">
                            <small class="form-text text-danger" v-if="!$v.address.secondLine.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 127 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.address.postcode')" for="address-postcode">Postcode</label>
                        <input type="text" class="form-control" name="postcode" id="address-postcode"
                            :class="{'valid': !$v.address.postcode.$invalid, 'invalid': $v.address.postcode.$invalid }" v-model="$v.address.postcode.$model"  required/>
                        <div v-if="$v.address.postcode.$anyDirty && $v.address.postcode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.address.postcode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.address.postcode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 10 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('mymentorApp.address.city')" for="address-city">City</label>
                        <input type="text" class="form-control" name="city" id="address-city"
                            :class="{'valid': !$v.address.city.$invalid, 'invalid': $v.address.city.$invalid }" v-model="$v.address.city.$model"  required/>
                        <div v-if="$v.address.city.$anyDirty && $v.address.city.$invalid">
                            <small class="form-text text-danger" v-if="!$v.address.city.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.address.city.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 127 characters.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.address.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./address-update.component.ts">
</script>
