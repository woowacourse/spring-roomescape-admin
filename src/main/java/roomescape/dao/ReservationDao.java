package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Repository
public class ReservationDao implements ReservationRepository {
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

    public Reservation add(Reservation reservation) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", reservation.getName().getName())
                .addValue("reservation_date", reservation.getDate().getDate())
                .addValue("time_id", reservation.getTime().getId());
        Long savedId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Reservation(savedId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Reservation> findAllByDateTime(Reservation reservation) {
        String sql = "SELECT r.id as reservation_id, r.name, r.reservation_date, t.id as time_id, t.start_at as time_value" +
                " FROM reservation as r" +
                " inner join reservation_time as t" +
                " on r.time_id = t.id" +
                " WHERE r.reservation_date = ?" +
                " AND r.time_id = ?";
        return jdbcTemplate.query(sql, reservationRowMapper, reservation.getDate().getDate(), reservation.getTime().getId().longValue());
    }
}
