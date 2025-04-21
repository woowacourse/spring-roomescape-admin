package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class Reservations {
    private final JdbcTemplate jdbcTemplate;

    public Reservations(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Reservation newReservation) {
        List<Reservation> allReservations = findAll();
        if (allReservations.stream().anyMatch(reservation -> reservation.isDuplicatedWith(newReservation))) {
            throw new IllegalArgumentException("이미 예약이 존재하는 날짜입니다.");
        }
        String query = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, newReservation.name(), newReservation.date(), newReservation.time());
    }

    public void deleteById(final Long id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        final int deletedCount = jdbcTemplate.update(query, id);
        if (deletedCount == 0) {
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
