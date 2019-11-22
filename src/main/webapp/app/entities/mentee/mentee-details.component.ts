import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMentee } from '@/shared/model/mentee.model';
import MenteeService from './mentee.service';

import UserManagementService from '@/admin/user-management/user-management.service';
import { IUser } from '@/shared/model/user.model';

import AddressService from '../address/address.service';
import { IAddress } from '@/shared/model/address.model';

import SkillService from '../skill/skill.service';

import EduStoneService from '../edu-stone/edu-stone.service';
import { IEduStone } from '@/shared/model/edu-stone.model';

import { library } from '@fortawesome/fontawesome-svg-core';

import { faPhone, faMobileAlt, faEnvelope, faLink, faMapMarkerAlt } from '@fortawesome/free-solid-svg-icons';

import { faXing, faLinkedinIn } from '@fortawesome/free-brands-svg-icons';

@Component
export default class MenteeDetails extends Vue {
  @Inject('menteeService') private menteeService: () => MenteeService;
  public mentee: IMentee = {};

  @Inject('userService') private userManagementService: () => UserManagementService;
  public user: any = {};

  @Inject('addressService') private addressService: () => AddressService;
  public address: IAddress = {};

  @Inject('skillService') private skillService: () => SkillService;
  public skills: any = [];

  @Inject('eduStoneService') private eduStoneService: () => EduStoneService;

  public eduStones: IEduStone[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.menteeId) {
        vm.retrieveMentee(to.params.menteeId);

        vm.initFontAwesome();
      }
    });
  }

  public retrieveMentee(menteeId) {
    this.menteeService()
      .find(menteeId)
      .then(res => {
        this.mentee = res;
        this.initRelationships();
      });
  }

  private initFontAwesome() {
    library.add(faPhone);
    library.add(faMobileAlt);
    library.add(faEnvelope);
    library.add(faXing);
    library.add(faLinkedinIn);
    library.add(faLink);
    library.add(faMapMarkerAlt);
  }

  public async initRelationships() {
    if (this.mentee.addressId) {
      this.addressService()
        .find(this.mentee.addressId)
        .then(res => {
          this.address = res;
        });
    }

    if (this.mentee.userLogin) {
      this.userManagementService()
        .get(this.mentee.userLogin)
        .then(res => {
          this.user = res.data;
        });
    }

    if (this.mentee.possessedSkills) {
      for (var i = 0; i < this.mentee.possessedSkills.length; i++) {
        this.skills.push({
          possessedSkill: this.mentee.possessedSkills[i],
          skill: await this.skillService()
            .find(this.mentee.possessedSkills[i].skillId)
            .then(res => {
              return res;
            })
        });
      }
    }

    // NOTE: Couldn't figure out the query so I'm retrieving all eduStones and filtering it here
    // this.eduStoneService()
    //   .search('menteeId.equalls=1')
    //   .then(res => {
    //     this.eduStones = res;
    //   })

    // TOTO: Reimplement this
    this.eduStoneService()
      .retrieve()
      .then(res => {
        var all = res.data;

        for (var i = 0; i < all.length; i++) {
          if (all[i].menteeId == this.mentee.id) {
            this.eduStones.push(all[i]);
          }
        }
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
