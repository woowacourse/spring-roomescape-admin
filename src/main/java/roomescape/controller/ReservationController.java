package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.util.CustomDateTimeFormatter;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) ->
            new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    CustomDateTimeFormatter.getLocalDate(rs.getString("date")),
                    CustomDateTimeFormatter.getLocalTime(rs.getString("time")
                    ));

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<FindReservationResponse> getReservation() {
        List<Reservation> reservations = jdbcTemplate.query("SELECT * FROM reservation", reservationRowMapper);
        return reservations.stream()
                .map(FindReservationResponse::of)
                .toList();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public CreateReservationResponse createReservation(
            @RequestBody CreateReservationRequest createReservationRequest) {
        Reservation newReservation = createReservationRequest.to(index.getAndIncrement());
        reservations.add(newReservation);
        return CreateReservationResponse.of(newReservation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteReservation(@PathVariable Long id) {
        reservations.removeIf(reservation -> reservation.isIdOf(id));
    }
}
