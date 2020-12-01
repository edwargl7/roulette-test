package com.mt.roulette.service;

import com.mt.roulette.domain.DBet;
import com.mt.roulette.persistence.IBetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetService implements IBetRepository {
    @Autowired
    private IBetRepository betRepository;

    @Override
    public List<DBet> getAll() {
        return betRepository.getAll();
    }

    @Override
    public List<DBet> getAllByRoulette(int rouletteId) {
        return betRepository.getAllByRoulette(rouletteId);
    }

    @Override
    public DBet get(int id) {
        return betRepository.get(id);
    }

    @Override
    public DBet create(int userId, int rouletteId, DBet bet) {
        return betRepository.create(userId, rouletteId, bet);
    }
}