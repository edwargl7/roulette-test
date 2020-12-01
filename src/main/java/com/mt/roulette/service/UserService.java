package com.mt.roulette.service;

import com.mt.roulette.domain.DUser;
import com.mt.roulette.persistence.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    public IUserRepository userRepository;

    @Override
    public List<DUser> getAll() {
        return userRepository.getAll();
    }

    @Override
    public DUser get(int id) {
        return userRepository.get(id);
    }

    @Override
    public DUser create(DUser user) {
        return userRepository.create(user);
    }
}
