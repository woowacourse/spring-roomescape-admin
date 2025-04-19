package roomescape.reservation.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.Reservations;

@Repository
public class ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Reservations findAll() {
        List<Reservation> reservations = jdbcTemplate.query(
                "select * from reservation", (resultSet, rowNum) ->
                        new Reservation(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                LocalDateTime.of(
                                        LocalDate.parse(resultSet.getString("date")),
                                        LocalTime.parse(resultSet.getString("time"))
                                )
                        ));
        return new Reservations(reservations);
    }
}
