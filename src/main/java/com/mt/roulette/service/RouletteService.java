package com.mt.roulette.service;

import com.mt.roulette.domain.DBet;
import com.mt.roulette.domain.DRoulette;
import com.mt.roulette.persistence.IBetRepository;
import com.mt.roulette.persistence.IRouletteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RouletteService implements IRouletteService {
    @Autowired
    private IBetRepository betRepository;

    @Autowired
    private IRouletteRepository rouletteRepository;

    @Override
    public List<DRoulette> getAll() {
        return rouletteRepository.getAll();
    }

    @Override
    public DRoulette get(int id) {
        return rouletteRepository.get(id);
    }

    @Override
    public int create() {
        return rouletteRepository.create();
    }

    @Override
    public boolean opening(int id) {
        return rouletteRepository.opening(id);
    }
}
