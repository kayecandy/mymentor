import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMentee } from '@/shared/model/mentee.model';
import MenteeService from './mentee.service';

@Component
export default class MenteeDetails extends Vue {
  @Inject('menteeService') private menteeService: () => MenteeService;
  public mentee: IMentee = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.menteeId) {
        vm.retrieveMentee(to.params.menteeId);
      }
    });
  }

  public retrieveMentee(menteeId) {
    this.menteeService()
      .find(menteeId)
      .then(res => {
        this.mentee = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
