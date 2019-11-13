import axios from 'axios';

import { IEduStone } from '@/shared/model/edu-stone.model';

const baseApiUrl = 'api/edu-stones';
const baseSearchApiUrl = 'api/_search/edu-stones?query=';

export default class EduStoneService {
  public search(query): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public find(id: number): Promise<IEduStone> {
    return new Promise<IEduStone>(resolve => {
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

  public create(entity: IEduStone): Promise<IEduStone> {
    return new Promise<IEduStone>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IEduStone): Promise<IEduStone> {
    return new Promise<IEduStone>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
