package com.mt.roulette.persistence;

import com.mt.roulette.domain.DUser;

import java.util.List;

public interface IUserRepository {
    List<DUser> getAll();
    DUser get(int id);
    DUser create(DUser user);
}
