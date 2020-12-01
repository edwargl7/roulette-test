package com.mt.roulette.service;

import com.mt.roulette.domain.DUser;

import java.util.List;

public interface IUserService {
    List<DUser> getAll();
    DUser get(int id);
    DUser create(DUser user);
}
