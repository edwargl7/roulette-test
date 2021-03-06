package com.mt.roulette.persistence;

import com.mt.roulette.domain.DRoulette;

import java.util.List;

public interface IRouletteRepository {
    List<DRoulette> getAll();
    DRoulette get(int id);
    int create();
    boolean opening(int id);
    boolean closing(int id);
}
