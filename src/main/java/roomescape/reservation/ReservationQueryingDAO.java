package roomescape.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.reservationtime.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationQueryingDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationQueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getObject("start_at", LocalTime.class)
        );

        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getObject("date", LocalDate.class),
                reservationTime
        );
    };

    public Reservation findReservationById(long id) {
        String sql = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at " +
                "from reservation as r " +
                "inner join reservation_time as t on r.time_id = t.id " +
                "where r.id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public List<Reservation> findAllReservations() {
        String sql = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at " +
                "from reservation as r " +
                "inner join reservation_time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
