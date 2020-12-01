package com.mt.roulette.persistence;

import com.mt.roulette.domain.DRoulette;
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
public class RouletteRepository implements IRouletteRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RouletteRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<DRoulette> getAll() {
        final String sql = "SELECT * FROM roulettes";

        List<DRoulette> list = this.jdbcTemplate.query(sql, new RowMapper<DRoulette>() {
            @Override
            public DRoulette mapRow(ResultSet rs, int rowNum) throws SQLException {
                final DRoulette domain = new DRoulette();
                domain.setId(rs.getInt("id"));
                domain.setIsOpen(rs.getBoolean("is_open"));
                domain.setIsActive(rs.getBoolean("is_active"));
                return domain;
            }
        });
        return list;
    }

    @Override
    public DRoulette get(int id) {
        final String sql = "SELECT * FROM roulettes WHERE id = ?";
        try {
            final DRoulette obj = jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<DRoulette>() {
                @Override
                public DRoulette mapRow(ResultSet rs, int rowNum) throws SQLException {
                    final DRoulette domain = new DRoulette();
                    domain.setId(rs.getInt("id"));
                    domain.setIsOpen(rs.getBoolean("is_open"));
                    domain.setIsActive(rs.getBoolean("is_active"));
                    return domain;
                }
            });
            return obj;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public DRoulette create(DRoulette roulette) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO roulettes (is_open, is_active) VALUES (?, ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(sql, new String[]{"id"});
                statement.setBoolean(1, roulette.getIsOpen());
                statement.setBoolean(2, roulette.getIsActive());
                return statement;
            }
        }, holder);
        roulette.setId(holder.getKey().intValue());
        return roulette;
    }
}
