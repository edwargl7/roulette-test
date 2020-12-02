package com.mt.roulette.persistence;

import com.mt.roulette.domain.DBet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BetRepository implements IBetRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BetRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private DBet getWinner(int rouletteId, String color) {
        final String sqlS = "SELECT b.id, b.final_money FROM bets b WHERE roulette_id = (?) AND chosen_value = (?)";
        try {
            return jdbcTemplate.queryForObject(sqlS, new Object[]{rouletteId, color}, new RowMapper<DBet>() {
                @Override
                public DBet mapRow(ResultSet rs, int rowNum) throws SQLException {
                    final DBet domain = new DBet();
                    domain.setId(rs.getInt("id"));
                    domain.setFinalMoney(rs.getDouble("final_money"));
                    return domain;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private void updateLoser(int rouletteId, String color, String numberStr) {
        final String sqlU = "UPDATE bets SET final_money = (?) WHERE roulette_id = (?) AND chosen_value NOT IN (?, ?)";
        jdbcTemplate.update(sqlU, 0, rouletteId, color, numberStr);
    }

    private void updateWinnerByColor(int rouletteId, String color) {
        DBet bet = getWinner(rouletteId, color);
        if (bet != null){
            final String sqlU = "UPDATE bets SET final_money = (?) WHERE id = (?)";
            double award = 0.0;
            award = bet.getFinalMoney() * 1.8;
            jdbcTemplate.update(sqlU, award, bet.getId());
        }
    }

    private void updateWinnerByNumber(int rouletteId, String numberStr) {
        DBet bet = getWinner(rouletteId, numberStr);
        if (bet != null){
            final String sqlU = "UPDATE bets SET final_money = (?) WHERE id = (?)";
            double award = 0.0;
            award = bet.getFinalMoney() * 5;
            jdbcTemplate.update(sqlU, award, bet.getId());
        }
    }

    @Override
    public List<DBet> getAll() {
        final String sql = "SELECT * FROM bets";

        List<DBet> listBet = this.jdbcTemplate.query(sql, new RowMapper<DBet>() {
            @Override
            public DBet mapRow(ResultSet rs, int rowNum) throws SQLException {
                final DBet domain = new DBet();
                domain.setId(rs.getInt("id"));
                domain.setRouletteId(rs.getInt("roulette_id"));
                domain.setUserId(rs.getInt("user_id"));
                domain.setMoneyBet(rs.getDouble("money_bet"));
                domain.setFinalMoney(rs.getDouble("final_money"));
                domain.setChosenValue(rs.getString("chosen_value"));
                domain.setBetByNumber(rs.getBoolean("bet_by_number"));
                return domain;
            }
        });
        return listBet;
    }

    @Override
    public List<DBet> getAllByRoulette(int rouletteId) {
        final String sql = "SELECT * FROM bets WHERE roulette_id = ?";
        List<DBet> listBet = this.jdbcTemplate.query(sql, new Object[]{rouletteId}, new RowMapper<DBet>() {
            @Override
            public DBet mapRow(ResultSet rs, int rowNum) throws SQLException {
                final DBet domain = new DBet();
                domain.setId(rs.getInt("id"));
                domain.setRouletteId(rs.getInt("roulette_id"));
                domain.setUserId(rs.getInt("user_id"));
                domain.setMoneyBet(rs.getDouble("money_bet"));
                domain.setFinalMoney(rs.getDouble("final_money"));
                domain.setChosenValue(rs.getString("chosen_value"));
                domain.setBetByNumber(rs.getBoolean("bet_by_number"));
                return domain;
            }
        });
        return listBet;
    }

    @Override
    public DBet get(int id) {
        final String sql = "SELECT * FROM bets WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<DBet>() {
                @Override
                public DBet mapRow(ResultSet rs, int rowNum) throws SQLException {
                    final DBet domain = new DBet();
                    domain.setId(rs.getInt("id"));
                    domain.setRouletteId(rs.getInt("roulette_id"));
                    domain.setUserId(rs.getInt("user_id"));
                    domain.setMoneyBet(rs.getDouble("money_bet"));
                    domain.setFinalMoney(rs.getDouble("final_money"));
                    domain.setChosenValue(rs.getString("chosen_value"));
                    domain.setBetByNumber(rs.getBoolean("bet_by_number"));
                    return domain;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public DBet create(int userId, int rouletteId, DBet bet) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO bets (roulette_id, user_id, money_bet, final_money, " +
                "chosen_value, bet_by_number) VALUES (?, ?, ?, ?, ?, ?)";
        bet.setFinalMoney(bet.getMoneyBet());
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(sql, new String[]{"id"});
                statement.setInt(1, rouletteId);
                statement.setInt(2, userId);
                statement.setDouble(3, bet.getMoneyBet());
                statement.setDouble(4, bet.getFinalMoney());
                statement.setString(5, bet.getChosenValue());
                statement.setBoolean(6, bet.getBetByNumber());
                return statement;
            }
        }, holder);
        try {
            bet.setId(holder.getKey().intValue());
            return bet;
        } catch (NullPointerException e) {
            return bet;
        }
    }

    @Override
    public List<DBet> setWinningAndLosingBet(int rouletteId, Map<String, String> winningNumberColor) {
        String color = winningNumberColor.get("color");
        String number = winningNumberColor.get("number");
        updateLoser(rouletteId, color, number);
        updateWinnerByColor(rouletteId, color);
        updateWinnerByNumber(rouletteId, number);
        return getAllByRoulette(rouletteId);
    }
}
