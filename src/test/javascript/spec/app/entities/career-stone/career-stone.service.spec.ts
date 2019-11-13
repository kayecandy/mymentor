/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import CareerStoneService from '@/entities/career-stone/career-stone.service';
import { CareerStone } from '@/shared/model/career-stone.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('CareerStone Service', () => {
    let service: CareerStoneService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new CareerStoneService();
      currentDate = new Date();

      elemDefault = new CareerStone(0, 'AAAAAAA', currentDate, currentDate, false, 'AAAAAAA', 'AAAAAAA');
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
      it('should create a CareerStone', async () => {
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

      it('should update a CareerStone', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            fromDate: format(currentDate, DATE_FORMAT),
            toDate: format(currentDate, DATE_FORMAT),
            stillWorkingHere: true,
            location: 'BBBBBB',
            description: 'BBBBBB'
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
      it('should return a list of CareerStone', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            fromDate: format(currentDate, DATE_FORMAT),
            toDate: format(currentDate, DATE_FORMAT),
            stillWorkingHere: true,
            location: 'BBBBBB',
            description: 'BBBBBB'
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
      it('should delete a CareerStone', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
