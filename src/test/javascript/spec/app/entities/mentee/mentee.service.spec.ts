/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import MenteeService from '@/entities/mentee/mentee.service';
import { Mentee } from '@/shared/model/mentee.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('Mentee Service', () => {
    let service: MenteeService;
    let elemDefault;
    beforeEach(() => {
      service = new MenteeService();

      elemDefault = new Mentee(0, 'AAAAAAA', 'AAAAAAA', false, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a Mentee', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a Mentee', async () => {
        const returnedFromService = Object.assign(
          {
            mobilePhone: 'BBBBBB',
            landline: 'BBBBBB',
            profileVisibleInInternet: true,
            ownWebsiteUrl: 'BBBBBB',
            xingProfileUrl: 'BBBBBB',
            linkedInProfileUrl: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of Mentee', async () => {
        const returnedFromService = Object.assign(
          {
            mobilePhone: 'BBBBBB',
            landline: 'BBBBBB',
            profileVisibleInInternet: true,
            ownWebsiteUrl: 'BBBBBB',
            xingProfileUrl: 'BBBBBB',
            linkedInProfileUrl: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a Mentee', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
