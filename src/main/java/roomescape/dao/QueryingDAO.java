package roomescape.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class QueryingDAO {
    private JdbcTemplate jdbcTemplate;

    public QueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public ReservationTime findTimeById(Long reservationId) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new ReservationTime(
                            resultSet.getLong("id"),
                            resultSet.getString("start_at")
                    ),
                reservationId
                );
    }

    public List<Reservation> findAllReservations() {
        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                INNER JOIN reservation_time as t
                  ON r.time_id = t.id
                """;
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            resultSet.getLong("time_id"),
                            resultSet.getString("time_value")
                    );
                    Reservation reservation = new Reservation(
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            reservationTime
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
                            resultSet.getLong("id"),
                            resultSet.getString("start_at")
                    );
                    return reservationTime;
                });
    }
}
