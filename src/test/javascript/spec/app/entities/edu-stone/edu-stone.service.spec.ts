/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import EduStoneService from '@/entities/edu-stone/edu-stone.service';
import { EduStone } from '@/shared/model/edu-stone.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('EduStone Service', () => {
    let service: EduStoneService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new EduStoneService();
      currentDate = new Date();

      elemDefault = new EduStone(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fromDate: format(currentDate, DATE_FORMAT),
            toDate: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a EduStone', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fromDate: format(currentDate, DATE_FORMAT),
            toDate: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a EduStone', async () => {
        const returnedFromService = Object.assign(
          {
            degree: 'BBBBBB',
            grade: 'BBBBBB',
            fromDate: format(currentDate, DATE_FORMAT),
            toDate: format(currentDate, DATE_FORMAT),
            description: 'BBBBBB',
            activities: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of EduStone', async () => {
        const returnedFromService = Object.assign(
          {
            degree: 'BBBBBB',
            grade: 'BBBBBB',
            fromDate: format(currentDate, DATE_FORMAT),
            toDate: format(currentDate, DATE_FORMAT),
            description: 'BBBBBB',
            activities: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a EduStone', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
