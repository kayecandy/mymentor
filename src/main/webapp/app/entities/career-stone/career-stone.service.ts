import axios from 'axios';

import { ICareerStone } from '@/shared/model/career-stone.model';

const baseApiUrl = 'api/career-stones';
const baseSearchApiUrl = 'api/_search/career-stones?query=';

export default class CareerStoneService {
  public search(query): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public find(id: number): Promise<ICareerStone> {
    return new Promise<ICareerStone>(resolve => {
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

  public create(entity: ICareerStone): Promise<ICareerStone> {
    return new Promise<ICareerStone>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ICareerStone): Promise<ICareerStone> {
    return new Promise<ICareerStone>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
