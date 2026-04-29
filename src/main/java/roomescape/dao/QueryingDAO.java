package roomescape.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class QueryingDAO {
    private JdbcTemplate jdbcTemplate;

    public QueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };

    public List<Reservation> findAllReservations() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );
                    return reservation;
                });
    }

    public List<ReservationTime> findAllTimes() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            resultSet.getString("start_at")
                    );
                    return reservationTime;
                });
    }
}
