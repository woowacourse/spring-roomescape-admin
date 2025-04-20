package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("reservations")
public class ReservationApiController {

    private final Reservations reservations = new Reservations();
    private final AtomicLong reservationId = new AtomicLong();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<ReservationResponse> getReservations() {
        String sql = "select * from reservation";
        List<Reservation> reservations = jdbcTemplate.query(
                sql,
                (resultSet, row) -> {
                    return new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getObject("datetime", LocalDateTime.class)
                    );
                }
        );

        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @PostMapping
    public ReservationResponse createReservation(@RequestBody ReservationRequest request) {
        Reservation created = request.toReservation(reservationId.incrementAndGet());
        reservations.add(created);

        return new ReservationResponse(created);
    }

    @DeleteMapping("{id}")
    public void deleteReservation(@PathVariable Long id, HttpServletResponse response) {
        Optional<Reservation> target = reservations.findById(id);

        if (target.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        reservations.remove(target.get());
    }
}
