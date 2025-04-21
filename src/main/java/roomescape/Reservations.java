package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class Reservations {
    private final List<Reservation> reservations;
    private final JdbcTemplate jdbcTemplate;

    public Reservations(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservations = new ArrayList<>();
    }

    public void save(Reservation newReservation) {
        if (reservations.stream().anyMatch(reservation -> reservation.isDuplicatedWith(newReservation))) {
            throw new IllegalArgumentException("이미 예약이 존재하는 날짜입니다.");
        }
        reservations.add(newReservation);
    }

    public void deleteById(final Long id) {
        if (!reservations.removeIf(reservation -> reservation.isSameId(id))) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }

    public List<Reservation> findAll() {
        String query = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            LocalDate date = resultSet.getObject("date", LocalDate.class);
            LocalTime time = resultSet.getObject("time", LocalTime.class);
            return new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    date,
                    time
            );
        });
    }
}
