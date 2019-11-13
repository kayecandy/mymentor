import axios from 'axios';

import { IEduInstitution } from '@/shared/model/edu-institution.model';

const baseApiUrl = 'api/edu-institutions';
const baseSearchApiUrl = 'api/_search/edu-institutions?query=';

export default class EduInstitutionService {
  public search(query): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public find(id: number): Promise<IEduInstitution> {
    return new Promise<IEduInstitution>(resolve => {
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

  public create(entity: IEduInstitution): Promise<IEduInstitution> {
    return new Promise<IEduInstitution>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IEduInstitution): Promise<IEduInstitution> {
    return new Promise<IEduInstitution>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
