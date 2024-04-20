package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.entity.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final JdbcTemplate jdbcTemplate;
    private final List<Reservation> reservations;
    private final AtomicLong index;

    public ReservationApiController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        reservations = new ArrayList<>();
        index = new AtomicLong(1);
    }

    @GetMapping
    public List<Reservation> findAllReservations() {
        String sql = "SELECT * FROM reservation";

        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    @PostMapping
    public Reservation addReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation reservation = reservationRequestDto.toEntity(index.getAndIncrement());
        reservations.add(reservation);
        return reservation;
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
    }
}
