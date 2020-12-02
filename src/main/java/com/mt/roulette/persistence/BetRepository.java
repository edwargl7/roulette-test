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

@Repository
public class BetRepository implements IBetRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BetRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
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
        final String sql = "INSERT INTO bets (roulette_id, user_id, money_bet, chosen_value," +
                " bet_by_number) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(sql, new String[]{"id"});
                statement.setInt(1, rouletteId);
                statement.setInt(2, userId);
                statement.setDouble(3, bet.getMoneyBet());
                statement.setString(4, bet.getChosenValue());
                statement.setBoolean(5, bet.getBetByNumber());
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
}
