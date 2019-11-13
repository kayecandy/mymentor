import axios from 'axios';

import { IPossessedSkill } from '@/shared/model/possessed-skill.model';

const baseApiUrl = 'api/possessed-skills';
const baseSearchApiUrl = 'api/_search/possessed-skills?query=';

export default class PossessedSkillService {
  public search(query): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public find(id: number): Promise<IPossessedSkill> {
    return new Promise<IPossessedSkill>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl).then(function(res) {
        resolve(res);
      });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.delete(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public create(entity: IPossessedSkill): Promise<IPossessedSkill> {
    return new Promise<IPossessedSkill>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IPossessedSkill): Promise<IPossessedSkill> {
    return new Promise<IPossessedSkill>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
