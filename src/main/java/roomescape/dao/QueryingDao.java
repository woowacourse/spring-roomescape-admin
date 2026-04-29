package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import java.util.List;

@Repository
public class QueryingDao {

    private final JdbcTemplate jdbcTemplate;

    public QueryingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );

        return reservation;
    };

    public int count() {
        String sql = "select count(*) from reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Reservation findById(Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public List<Reservation> findAll() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
