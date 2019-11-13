import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPossessedSkill } from '@/shared/model/possessed-skill.model';
import PossessedSkillService from './possessed-skill.service';

@Component
export default class PossessedSkillDetails extends Vue {
  @Inject('possessedSkillService') private possessedSkillService: () => PossessedSkillService;
  public possessedSkill: IPossessedSkill = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.possessedSkillId) {
        vm.retrievePossessedSkill(to.params.possessedSkillId);
      }
    });
  }

  public retrievePossessedSkill(possessedSkillId) {
    this.possessedSkillService()
      .find(possessedSkillId)
      .then(res => {
        this.possessedSkill = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
