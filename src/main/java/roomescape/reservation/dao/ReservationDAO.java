package roomescape.reservation.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.request.ReservationRequest;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT * from reservation";
        List<Reservation> reservations = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime()
                    );
                    return reservation;
                });
        return reservations;
    }

    public long insertReservation(final ReservationRequest reservationRequest) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservationRequest.name());
        parameters.put("date", Date.valueOf(reservationRequest.date()));
        parameters.put("time", Time.valueOf(reservationRequest.time()));
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return newId.longValue();
    }

    public void removeReservation(final long id) {
        String sql = "DELETE from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
