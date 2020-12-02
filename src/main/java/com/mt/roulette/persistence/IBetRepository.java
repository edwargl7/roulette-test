package com.mt.roulette.persistence;

import com.mt.roulette.domain.DBet;

import java.util.List;
import java.util.Map;

public interface IBetRepository {
    List<DBet> getAll();
    List<DBet> getAllByRoulette(int rouletteId);
    DBet get(int id);
    DBet create(int userId, int rouletteId, DBet bet);
    List<DBet> setWinningAndLosingBet(int rouletteId, Map<String, String> winningNumberColor);
}
