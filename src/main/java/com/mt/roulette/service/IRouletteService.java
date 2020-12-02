package com.mt.roulette.service;

import com.mt.roulette.domain.DBet;
import com.mt.roulette.domain.DRoulette;

import java.util.List;
import java.util.Map;

public interface IRouletteService {
    List<DRoulette> getAll();
    DRoulette get(int id);
    int create();
    boolean opening(int id);
}
