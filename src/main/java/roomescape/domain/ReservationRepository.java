package roomescape.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations;
    private final AtomicLong index;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationRepository() {
        reservations = new ArrayList<>();
        index = new AtomicLong(1L);
    }


    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM reservation", reservationRowMapper());
    }

    public Reservation findById(long id) {
        return jdbcTemplate.queryForObject("SELECT id, name, date, time FROM reservation WHERE id = ?",
                reservationRowMapper(), id);
    }

    public Reservation create(Reservation reservation) {
        Reservation createdReservation = new Reservation(index.getAndIncrement(), reservation.getName(),
                reservation.getDate(), reservation.getTime());
        reservations.add(createdReservation);
        return createdReservation;
    }

    public void remove(Reservation reservation) {
        reservations.remove(reservation);
    }

    public void removeAll() {
        reservations.clear();
        index.set(1L);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            );
            return reservation;
        };
    }
}
