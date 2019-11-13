import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate' // for vue-router 2.2+
]);
import Router from 'vue-router';
const Home = () => import('../core/home/home.vue');
const Error = () => import('../core/error/error.vue');
const Register = () => import('../account/register/register.vue');
const Activate = () => import('../account/activate/activate.vue');
const ResetPasswordInit = () => import('../account/reset-password/init/reset-password-init.vue');
const ResetPasswordFinish = () => import('../account/reset-password/finish/reset-password-finish.vue');
const ChangePassword = () => import('../account/change-password/change-password.vue');
const Settings = () => import('../account/settings/settings.vue');
const JhiUserManagementComponent = () => import('../admin/user-management/user-management.vue');
const JhiUserManagementViewComponent = () => import('../admin/user-management/user-management-view.vue');
const JhiUserManagementEditComponent = () => import('../admin/user-management/user-management-edit.vue');
const JhiConfigurationComponent = () => import('../admin/configuration/configuration.vue');
const JhiDocsComponent = () => import('../admin/docs/docs.vue');
const JhiHealthComponent = () => import('../admin/health/health.vue');
const JhiLogsComponent = () => import('../admin/logs/logs.vue');
const JhiAuditsComponent = () => import('../admin/audits/audits.vue');
const JhiMetricsComponent = () => import('../admin/metrics/metrics.vue');
/* tslint:disable */
// prettier-ignore
const Address = () => import('../entities/address/address.vue');
// prettier-ignore
const AddressUpdate = () => import('../entities/address/address-update.vue');
// prettier-ignore
const AddressDetails = () => import('../entities/address/address-details.vue');
// prettier-ignore
const Language = () => import('../entities/language/language.vue');
// prettier-ignore
const LanguageUpdate = () => import('../entities/language/language-update.vue');
// prettier-ignore
const LanguageDetails = () => import('../entities/language/language-details.vue');
// prettier-ignore
const Company = () => import('../entities/company/company.vue');
// prettier-ignore
const CompanyUpdate = () => import('../entities/company/company-update.vue');
// prettier-ignore
const CompanyDetails = () => import('../entities/company/company-details.vue');
// prettier-ignore
const EduInstitution = () => import('../entities/edu-institution/edu-institution.vue');
// prettier-ignore
const EduInstitutionUpdate = () => import('../entities/edu-institution/edu-institution-update.vue');
// prettier-ignore
const EduInstitutionDetails = () => import('../entities/edu-institution/edu-institution-details.vue');
// prettier-ignore
const Certificate = () => import('../entities/certificate/certificate.vue');
// prettier-ignore
const CertificateUpdate = () => import('../entities/certificate/certificate-update.vue');
// prettier-ignore
const CertificateDetails = () => import('../entities/certificate/certificate-details.vue');
// prettier-ignore
const CareerStone = () => import('../entities/career-stone/career-stone.vue');
// prettier-ignore
const CareerStoneUpdate = () => import('../entities/career-stone/career-stone-update.vue');
// prettier-ignore
const CareerStoneDetails = () => import('../entities/career-stone/career-stone-details.vue');
// prettier-ignore
const EduStone = () => import('../entities/edu-stone/edu-stone.vue');
// prettier-ignore
const EduStoneUpdate = () => import('../entities/edu-stone/edu-stone-update.vue');
// prettier-ignore
const EduStoneDetails = () => import('../entities/edu-stone/edu-stone-details.vue');
// prettier-ignore
const Skill = () => import('../entities/skill/skill.vue');
// prettier-ignore
const SkillUpdate = () => import('../entities/skill/skill-update.vue');
// prettier-ignore
const SkillDetails = () => import('../entities/skill/skill-details.vue');
// prettier-ignore
const PossessedSkill = () => import('../entities/possessed-skill/possessed-skill.vue');
// prettier-ignore
const PossessedSkillUpdate = () => import('../entities/possessed-skill/possessed-skill-update.vue');
// prettier-ignore
const PossessedSkillDetails = () => import('../entities/possessed-skill/possessed-skill-details.vue');
// prettier-ignore
const Mentee = () => import('../entities/mentee/mentee.vue');
// prettier-ignore
const MenteeUpdate = () => import('../entities/mentee/mentee-update.vue');
// prettier-ignore
const MenteeDetails = () => import('../entities/mentee/mentee-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/activate',
      name: 'Activate',
      component: Activate
    },
    {
      path: '/reset/request',
      name: 'ResetPasswordInit',
      component: ResetPasswordInit
    },
    {
      path: '/reset/finish',
      name: 'ResetPasswordFinish',
      component: ResetPasswordFinish
    },
    {
      path: '/account/password',
      name: 'ChangePassword',
      component: ChangePassword,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/account/settings',
      name: 'Settings',
      component: Settings,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/admin/user-management',
      name: 'JhiUser',
      component: JhiUserManagementComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/new',
      name: 'JhiUserCreate',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/edit',
      name: 'JhiUserEdit',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/view',
      name: 'JhiUserView',
      component: JhiUserManagementViewComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/docs',
      name: 'JhiDocsComponent',
      component: JhiDocsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/audits',
      name: 'JhiAuditsComponent',
      component: JhiAuditsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-health',
      name: 'JhiHealthComponent',
      component: JhiHealthComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/logs',
      name: 'JhiLogsComponent',
      component: JhiLogsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-metrics',
      name: 'JhiMetricsComponent',
      component: JhiMetricsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-configuration',
      name: 'JhiConfigurationComponent',
      component: JhiConfigurationComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    }
    ,
    {
      path: '/entity/address',
      name: 'Address',
      component: Address,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/address/new',
      name: 'AddressCreate',
      component: AddressUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/address/:addressId/edit',
      name: 'AddressEdit',
      component: AddressUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/address/:addressId/view',
      name: 'AddressView',
      component: AddressDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/language',
      name: 'Language',
      component: Language,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/language/new',
      name: 'LanguageCreate',
      component: LanguageUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/language/:languageId/edit',
      name: 'LanguageEdit',
      component: LanguageUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/language/:languageId/view',
      name: 'LanguageView',
      component: LanguageDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/company',
      name: 'Company',
      component: Company,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/company/new',
      name: 'CompanyCreate',
      component: CompanyUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/company/:companyId/edit',
      name: 'CompanyEdit',
      component: CompanyUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/company/:companyId/view',
      name: 'CompanyView',
      component: CompanyDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/edu-institution',
      name: 'EduInstitution',
      component: EduInstitution,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/edu-institution/new',
      name: 'EduInstitutionCreate',
      component: EduInstitutionUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/edu-institution/:eduInstitutionId/edit',
      name: 'EduInstitutionEdit',
      component: EduInstitutionUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/edu-institution/:eduInstitutionId/view',
      name: 'EduInstitutionView',
      component: EduInstitutionDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/certificate',
      name: 'Certificate',
      component: Certificate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/certificate/new',
      name: 'CertificateCreate',
      component: CertificateUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/certificate/:certificateId/edit',
      name: 'CertificateEdit',
      component: CertificateUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/certificate/:certificateId/view',
      name: 'CertificateView',
      component: CertificateDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/career-stone',
      name: 'CareerStone',
      component: CareerStone,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/career-stone/new',
      name: 'CareerStoneCreate',
      component: CareerStoneUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/career-stone/:careerStoneId/edit',
      name: 'CareerStoneEdit',
      component: CareerStoneUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/career-stone/:careerStoneId/view',
      name: 'CareerStoneView',
      component: CareerStoneDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/edu-stone',
      name: 'EduStone',
      component: EduStone,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/edu-stone/new',
      name: 'EduStoneCreate',
      component: EduStoneUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/edu-stone/:eduStoneId/edit',
      name: 'EduStoneEdit',
      component: EduStoneUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/edu-stone/:eduStoneId/view',
      name: 'EduStoneView',
      component: EduStoneDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/skill',
      name: 'Skill',
      component: Skill,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/skill/new',
      name: 'SkillCreate',
      component: SkillUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/skill/:skillId/edit',
      name: 'SkillEdit',
      component: SkillUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/skill/:skillId/view',
      name: 'SkillView',
      component: SkillDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/possessed-skill',
      name: 'PossessedSkill',
      component: PossessedSkill,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/possessed-skill/new',
      name: 'PossessedSkillCreate',
      component: PossessedSkillUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/possessed-skill/:possessedSkillId/edit',
      name: 'PossessedSkillEdit',
      component: PossessedSkillUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/possessed-skill/:possessedSkillId/view',
      name: 'PossessedSkillView',
      component: PossessedSkillDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/mentee',
      name: 'Mentee',
      component: Mentee,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/mentee/new',
      name: 'MenteeCreate',
      component: MenteeUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/mentee/:menteeId/edit',
      name: 'MenteeEdit',
      component: MenteeUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/mentee/:menteeId/view',
      name: 'MenteeView',
      component: MenteeDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ]
});
