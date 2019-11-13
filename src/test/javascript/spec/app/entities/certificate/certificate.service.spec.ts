/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import CertificateService from '@/entities/certificate/certificate.service';
import { Certificate } from '@/shared/model/certificate.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('Certificate Service', () => {
    let service: CertificateService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new CertificateService();
      currentDate = new Date();

      elemDefault = new Certificate(0, 'AAAAAAA', currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            issueDate: format(currentDate, DATE_FORMAT),
            expireDate: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a Certificate', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            issueDate: format(currentDate, DATE_FORMAT),
            expireDate: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            issueDate: currentDate,
            expireDate: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a Certificate', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            issueDate: format(currentDate, DATE_FORMAT),
            expireDate: format(currentDate, DATE_FORMAT),
            url: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            issueDate: currentDate,
            expireDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of Certificate', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            issueDate: format(currentDate, DATE_FORMAT),
            expireDate: format(currentDate, DATE_FORMAT),
            url: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            issueDate: currentDate,
            expireDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a Certificate', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
