package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;

import java.util.List;

@Repository
public class ReservationDao {
    private static final String TABLE_NAME = "reservation";
    private static final String ID = "id";

    private final RowMapper<Reservation> reservationRowMapper;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, RowMapper<Reservation> reservationRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID);
        this.reservationRowMapper = reservationRowMapper;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.reservation_date, t.id as time_id, t.start_at as time_value" +
                " FROM reservation as r" +
                " inner join reservation_time as t" +
                " on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation findById(Long id) {
        String sql = "SELECT r.id as reservation_id, r.name, r.reservation_date, t.id as time_id, t.start_at as time_value" +
                " FROM reservation as r" +
                " inner join reservation_time as t" +
                " on r.time_id = t.id" +
                " WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public Long add(Reservation reservation) {
        return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(reservation)).longValue();
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
