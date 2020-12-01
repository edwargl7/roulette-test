package com.mt.roulette.service;

import com.mt.roulette.domain.DRoulette;

import java.util.List;

public interface IRouletteService {
    List<DRoulette> getAll();
    DRoulette get(int id);
    int create();
}
