package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationRequest;

import java.util.List;
import java.util.Optional;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(CreateReservationRequest request) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        return jdbcTemplate.update(sql,
                request.name(),
                request.date(),
                request.timeId());
    }

    public Optional<Reservation> findAnyByTimeId(int timeId) {
        String sql = "SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at " +
                "FROM reservation r " +
                "JOIN reservation_time t ON r.time_id = t.id " +
                "WHERE r.time_id = ? " +
                "LIMIT 1";
        try {
            Reservation reservation = jdbcTemplate.queryForObject(sql, getReservationRowMapper(), timeId);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Reservation findById(int id) {
        String sql = "SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at " +
                "FROM reservation r " +
                "JOIN reservation_time t ON r.time_id = t.id " +
                "WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, getReservationRowMapper(), id);
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at " +
                "FROM reservation as r " +
                "inner join reservation_time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, getReservationRowMapper());
    }

    private RowMapper<Reservation> getReservationRowMapper() {
        return (resultSet, rowNum) -> {
            Reservation reservation = new Reservation();
            reservation.setId(resultSet.getInt("id"));
            reservation.setName(resultSet.getString("name"));
            reservation.setDate(resultSet.getDate("date").toLocalDate());

            ReservationTime time = new ReservationTime();
            time.setId(resultSet.getInt("time_id"));
            time.setStartAt(resultSet.getTime("start_at").toLocalTime());
            reservation.setTime(time);

            return reservation;
        };
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
