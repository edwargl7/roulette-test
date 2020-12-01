package com.mt.roulette.persistence;

import com.mt.roulette.domain.DRoulette;
import com.mt.roulette.domain.DUser;
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
public class UserRepository implements IUserRepository{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<DUser> getAll() {
        final String sql = "SELECT * FROM users";

        List<DUser> listUser = this.jdbcTemplate.query(sql, new RowMapper<DUser>() {
            @Override
            public DUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                final DUser domain = new DUser();
                domain.setId(rs.getInt("id"));
                domain.setOther(rs.getString("other"));
                return domain;
            }
        });
        return listUser;
    }

    @Override
    public DUser get(int id) {
        final String sql = "SELECT * FROM users WHERE id = ?";
        try {
            final DUser obj = jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<DUser>() {
                @Override
                public DUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                    final DUser domain = new DUser();
                    domain.setId(rs.getInt("id"));
                    domain.setOther(rs.getString("other"));
                    return domain;
                }
            });
            return obj;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public DUser create(DUser user) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO users (other) VALUES (?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(sql, new String[]{"id"});
                statement.setString(1, user.getOther());
                return statement;
            }
        }, holder);
        try {
            user.setId(holder.getKey().intValue());
            return user;
        }catch (NullPointerException e){
            return user;
        }
    }
}
