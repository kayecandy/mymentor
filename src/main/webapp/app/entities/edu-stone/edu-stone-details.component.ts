import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEduStone } from '@/shared/model/edu-stone.model';
import EduStoneService from './edu-stone.service';

@Component
export default class EduStoneDetails extends Vue {
  @Inject('eduStoneService') private eduStoneService: () => EduStoneService;
  public eduStone: IEduStone = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.eduStoneId) {
        vm.retrieveEduStone(to.params.eduStoneId);
      }
    });
  }

  public retrieveEduStone(eduStoneId) {
    this.eduStoneService()
      .find(eduStoneId)
      .then(res => {
        this.eduStone = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
