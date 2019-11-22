<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="mentee">

                <div class="container mb-5">
                    <div class="cndce-header-container cndce-mentee-box">
                        <div class="cndce-header-background test"></div>
                        <div class="px-4 pb-4">
                            <div class="row mt-2">
                                <div class="col">
                                    <div class="cndce-mentee-image mr-2"></div>

                                    <a class="cndce-mentee-link" v-if="mentee.ownWebsiteUrl" :href="mentee.ownWebsiteUrl"><font-awesome-icon :icon="['fas', 'link']"></font-awesome-icon></a>

                                    <a class="cndce-mentee-link xing" v-if="mentee.xingProfileUrl" :href="mentee.xingProfileUrl"><font-awesome-icon :icon="['fab', 'xing']"></font-awesome-icon></a>

                                    <a class="cndce-mentee-link linkedin" v-if="mentee.linkedInProfileUrl" :href="mentee.linkedInProfileUrl"><font-awesome-icon :icon="['fab', 'linkedin-in']"></font-awesome-icon></a>

                                </div>
                                <div class="col text-right">
                                    <button type="submit"
                                            v-on:click.prevent="previousState()"
                                            class="btn btn-info">
                                        <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                                    </button>

                                    <router-link v-if="mentee.id" :to="{name: 'MenteeEdit', params: {menteeId: mentee.id}}" tag="button" class="btn btn-primary">
                                        <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                                    </router-link>
                                </div>
                                                            
                            </div>
                            <div class="cndce-header-content row mt-3">

                                <div class="col">

                                    <div class="cndce-mentee-username">@{{user.login}}</div>
                                    <h2>{{user.firstName}} {{user.lastName}}</h2>

                                    <div>
                                        <a :href="`mailto:${user.email}`"><font-awesome-icon icon="envelope"></font-awesome-icon> <span>{{user.email}}</span></a>
                                    </div>
                                </div>

                                <div class="col-4">

                                    <div class="cndce-mentee-detail"><font-awesome-icon class="cndce-mentee-detail-icon" icon="mobile-alt"></font-awesome-icon> <span>{{mentee.mobilePhone}}</span></div>
                                    <div class="cndce-mentee-detail"><font-awesome-icon class="cndce-mentee-detail-icon" icon="phone"></font-awesome-icon> <span>{{mentee.landline}}</span></div>

                                    <div class="cndce-mentee-detail"><font-awesome-icon class="cndce-mentee-detail-icon" icon="map-marker-alt"></font-awesome-icon><div class="d-inline-block">
                                        {{address.streetNumber}} {{address.street}}<br>
                                        <span v-if="address.secondLine">{{address.secondLine}}<br></span>
                                        {{address.city}} {{address.postcode}}
                                        
                                    </div></div>

                                    
                                    
                                    
                                </div>

                                 
                            </div>
                        </div>
                        
                    </div> <!-- .cndce-header-container -->

                    <div v-if="mentee.possessedSkills" class="cndce-skills-container cndce-mentee-box mt-5 p-4">

                        <h3 class="mb-3">Skills</h3>

                        <div v-for="(skill, i) in skills" :key="skill.possessedSkill.id" class="cndce-mentee-skill-item py-3">

                            <div>
                                <router-link :to="{name: 'SkillView', params: {skillId: skill.skill.id}}">{{skill.skill.name}}</router-link>

                                <span class="cndce-mentee-skill-type bg-dark">{{skill.skill.type}}</span>
                            </div>

                            {{skill.skill.description}}
                                


                            
                        </div>
                    </div> <!-- .cndce-skills-container -->


                    <div v-if="eduStones" class="cndce-edustones-container cndce-mentee-box mt-5 p-4">
                        <h3 class="mb-3">Education</h3>

                        <router-link :to="{name: 'EduStoneView', params: {eduStoneId: eduStone.id}}"  v-for="eduStone in eduStones" class="py-3 px-2 cndce-mentee-edustone">

                            <div class="row">
                                <div class="col-3">
                                    <span class="cndce-mentee-edustone-date">
                                        {{eduStone.fromDate}} to {{eduStone.toDate}}
                                    </span>
                                </div>

                                <div class="col">
                                    <router-link :to="{name: 'EduInstitutionView', params: {eduInstitutionId: eduStone.schoolId}}">{{eduStone.schoolName}}</router-link><br>
                                    {{eduStone.grade}}, {{eduStone.degree}}<br>

                                    <span class="cndce-mentee-edustone-description">{{eduStone.description}}</span>
                                </div>

                                <div class="col-2">
                                    <img class="w-100" src="/content/images/logo-jhipster.png">
                                </div>
                            </div>
                            
                            
                        </router-link>
                    </div>

                </div>
                
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./mentee-details.component.ts">
</script>

<style scoped>
    @import 'mentee-details.css'
</style>