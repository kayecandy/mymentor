import axios from 'axios';

import { IMentee } from '@/shared/model/mentee.model';

const baseApiUrl = 'api/mentees';
const baseSearchApiUrl = 'api/_search/mentees?query=';

export default class MenteeService {
  public search(query): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public find(id: number): Promise<IMentee> {
    return new Promise<IMentee>(resolve => {
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

  public create(entity: IMentee): Promise<IMentee> {
    return new Promise<IMentee>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IMentee): Promise<IMentee> {
    return new Promise<IMentee>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
