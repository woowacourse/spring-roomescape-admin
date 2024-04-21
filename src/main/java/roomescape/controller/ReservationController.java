package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationAddRequest;

@RestController
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public List<Reservation> getReservationList() {
        return jdbcTemplate.query("select * from reservation",
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime()
                    );
                    return reservation;
                });
    }

    @PostMapping("/reservations")
    public Reservation addReservation(@RequestBody ReservationAddRequest reservationAddRequest) {
        Reservation reservation = new Reservation(index.getAndIncrement(), reservationAddRequest.getName(),
                reservationAddRequest.getDate(), reservationAddRequest.getTime());

        reservations.add(reservation);
        return reservation;
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable("id") Long id) {
        Optional<Reservation> reservationOptional = reservations.stream()
                .filter(it -> it.equalId(id))
                .findFirst();

        if (reservationOptional.isPresent()) {
            reservations.remove(reservationOptional.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
