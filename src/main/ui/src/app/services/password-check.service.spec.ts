import { TestBed } from '@angular/core/testing';

import {PasswordCheckService, PasswordCheckStrength} from './password-check.service';

describe('PasswordCheckService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PasswordCheckService = TestBed.get(PasswordCheckService);
    expect(service).toBeTruthy();
  });

  it('should validate a short password', () => {
    const service: PasswordCheckService = TestBed.get(PasswordCheckService);
    expect(service.checkPasswordStrength('123')).toBe(PasswordCheckStrength.Short);
  });

  it('should validate a common password', () => {
    const service: PasswordCheckService = TestBed.get(PasswordCheckService);
    expect(service.checkPasswordStrength('1234567890')).toBe(PasswordCheckStrength.Common);
  });

  it('should validate a weak password', () => {
    const service: PasswordCheckService = TestBed.get(PasswordCheckService);
    expect(service.checkPasswordStrength('start123')).toBe(PasswordCheckStrength.Weak);
  });

  it('should validate a ok password', () => {
    const service: PasswordCheckService = TestBed.get(PasswordCheckService);
    expect(service.checkPasswordStrength('start123!')).toBe(PasswordCheckStrength.Ok);
  });

  it('should validate a strong password', () => {
    const service: PasswordCheckService = TestBed.get(PasswordCheckService);
    expect(service.checkPasswordStrength('Alef28#!83jUri!')).toBe(PasswordCheckStrength.Strong);
  });
});
