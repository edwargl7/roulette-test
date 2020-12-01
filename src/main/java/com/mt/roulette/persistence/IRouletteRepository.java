package com.mt.roulette.persistence;

import com.mt.roulette.domain.DRoulette;

import java.util.List;

public interface IRouletteRepository {
    List<DRoulette> getAll();
    DRoulette get(int id);
    DRoulette create(DRoulette roulette);
}
