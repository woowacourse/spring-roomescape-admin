package roomescape.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.dto.CreateReservationDto;
import roomescape.entity.Reservation;
import roomescape.exception.InvalidReservationException;

@Repository
public class ReservationRepository {
    private JdbcTemplate jdbcTemplate;

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getObject("dateTime", LocalDateTime.class)
                ));
    }

    public Reservation add(CreateReservationDto createReservationDto) {
        Reservation reservation = createReservationDto.toEntity(index.getAndIncrement());
        reservations.add(reservation);
        return reservation;
    }

    public void deleteById(Long id) {
        Reservation reservation = reservations.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(InvalidReservationException::new);

        reservations.remove(reservation);
    }
}
