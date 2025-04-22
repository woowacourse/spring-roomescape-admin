package roomescape;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.model.Entity;

@Repository
public class Dao {

    private final JdbcTemplate jdbcTemplate;

    public Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> List<T> getAll(String query, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(query, rowMapper);
    }

    public <T extends Entity> T save(String query, T object, PreparedStatementProvider psProvider) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[] {object.idColumnName()});
            return psProvider.provide(ps, object);
        }, keyHolder);
        return object.withId(keyHolder.getKey().longValue());
    }

    public void remove(String query, Long id) {
        jdbcTemplate.update(query, id);
    }
}
