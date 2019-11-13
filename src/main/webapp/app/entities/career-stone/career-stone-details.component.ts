import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICareerStone } from '@/shared/model/career-stone.model';
import CareerStoneService from './career-stone.service';

@Component
export default class CareerStoneDetails extends Vue {
  @Inject('careerStoneService') private careerStoneService: () => CareerStoneService;
  public careerStone: ICareerStone = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.careerStoneId) {
        vm.retrieveCareerStone(to.params.careerStoneId);
      }
    });
  }

  public retrieveCareerStone(careerStoneId) {
    this.careerStoneService()
      .find(careerStoneId)
      .then(res => {
        this.careerStone = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
