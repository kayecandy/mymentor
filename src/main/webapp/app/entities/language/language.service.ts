import axios from 'axios';

import { ILanguage } from '@/shared/model/language.model';

const baseApiUrl = 'api/languages';
const baseSearchApiUrl = 'api/_search/languages?query=';

export default class LanguageService {
  public search(query): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public find(id: number): Promise<ILanguage> {
    return new Promise<ILanguage>(resolve => {
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

  public create(entity: ILanguage): Promise<ILanguage> {
    return new Promise<ILanguage>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ILanguage): Promise<ILanguage> {
    return new Promise<ILanguage>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
