package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date")),
            LocalTime.parse(resultSet.getString("time"))
    );

    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>(List.of(
            new Reservation(index.getAndIncrement(), "브라운", LocalDate.parse("2023-01-01"), LocalTime.parse("10:00")),
            new Reservation(index.getAndIncrement(), "브라운", LocalDate.parse("2023-01-02"), LocalTime.parse("11:00"))
    ));

    public List<Reservation> findAll() {
        String sql = "select * from reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper);

        return Collections.unmodifiableList(reservations);
    }

    public Reservation save(Reservation reservation) {
        Reservation savedReservation = new Reservation(index.getAndIncrement(), reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(savedReservation);
        return savedReservation;
    }

    public void deleteById(Long id) {
        Reservation reservation = findById(id);
        reservations.remove(reservation);
    }

    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 아아디입니다."));
    }
}
