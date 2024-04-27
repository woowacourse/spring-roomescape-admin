package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationId;

import java.util.List;
import java.util.Optional;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate()
        );
        ReservationTime time = new ReservationTime(
                resultSet.getInt("time_id"),
                resultSet.getTime("start_at").toLocalTime()
        );
        reservation.setTime(time);
        return reservation;
    };

    public ReservationId create(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId());
        long createdId = jdbcInsert.executeAndReturnKey(params).longValue();
        return new ReservationId(createdId);
    }

    public Optional<Reservation> findAnyByTimeId(long timeId) {
        String sql = "SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at " +
                "FROM reservation r " +
                "JOIN reservation_time t ON r.time_id = t.id " +
                "WHERE r.time_id = ? " +
                "LIMIT 1";
        try {
            Reservation reservation = jdbcTemplate.queryForObject(sql, reservationRowMapper, timeId);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Reservation findById(long id) {
        String sql = "SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at " +
                "FROM reservation r " +
                "JOIN reservation_time t ON r.time_id = t.id " +
                "WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at " +
                "FROM reservation as r " +
                "inner join reservation_time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public void delete(long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
