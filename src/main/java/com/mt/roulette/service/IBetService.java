package com.mt.roulette.service;

import com.mt.roulette.domain.DBet;

import java.util.List;
import java.util.Map;

public interface IBetService {
    List<DBet> getAll();
    List<DBet> getAllByRoulette(int rouletteId);
    List<DBet> setWinningAndLosingBet(int rouletteId, Map<String, String> winningNumberColor);
    DBet get(int id);
    DBet create(DBet bet);
}
