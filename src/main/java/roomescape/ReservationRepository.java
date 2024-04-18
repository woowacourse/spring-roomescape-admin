package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

    private static final String TABLE_NAME = "reservation";
    private static final RowMapper<Reservation> ROW_MAPPER = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("date"),
            resultSet.getString("time"));

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong autoIncreaseId = new AtomicLong(1);
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM %s".formatted(TABLE_NAME), ROW_MAPPER);
    }

    public Reservation create(Reservation reservation) {
        Reservation createdReservation = reservation.toEntity(autoIncreaseId.getAndIncrement());
        reservations.add(createdReservation);
        return createdReservation;
    }

    public void deleteById(Long id) {
        Reservation searchedReservation = findById(id);
        reservations.remove(searchedReservation);
    }

    private Reservation findById(Long id) {
        Reservation reservation = jdbcTemplate.queryForObject("SELECT id, name, date, time FROM %s WHERE id = ?",
                ROW_MAPPER, id);

        if (reservation == null) {
            throw new IllegalStateException("해당 예약이 없습니다.");
        }
        return reservation;
    }

}
