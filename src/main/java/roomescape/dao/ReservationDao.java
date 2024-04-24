package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.controller.dto.CreateReservationRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.util.List;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(CreateReservationRequest request) {
        return jdbcTemplate.update("insert into reservation (name, date, time_id) values (?, ?, ?)",
                request.name(),
                request.date(),
                request.timeId());
    }

    public List<Reservation> findAllByTimeId(int timeId) {
        return jdbcTemplate.query("SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at " +
                        "FROM reservation as r " +
                        "inner join reservation_time as t on r.time_id = t.id " +
                        "WHERE t.id = ?",
                getReservationRowMapper(), timeId);
    }

    public Reservation findById(int id) {
        return jdbcTemplate.queryForObject("SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at " +
                        "FROM reservation r " +
                        "JOIN reservation_time t ON r.time_id = t.id " +
                        "WHERE r.id = ?",
                getReservationRowMapper(), id);
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at " +
                        "FROM reservation as r " +
                        "inner join reservation_time as t on r.time_id = t.id",
                getReservationRowMapper()
        );
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
