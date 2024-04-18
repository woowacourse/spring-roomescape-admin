package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationSaveRequest;
import roomescape.mapper.ReservationMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final ReservationMapper reservationMapper = new ReservationMapper();
    private final AtomicLong index = new AtomicLong(1);
    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date")),
            LocalTime.parse(resultSet.getString("time"))
    );
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/reservation")
    public String getAdminReservations() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<Reservation> reservations = jdbcTemplate.query("SELECT * FROM reservation", reservationRowMapper);
        List<ReservationResponse> responses = reservations.stream()
                .map(reservationMapper::mapToResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationSaveRequest request) {
        long id = index.getAndIncrement();
        Reservation reservation = reservationMapper.mapToReservation(id, request);
        jdbcTemplate.update("INSERT INTO reservation (id, name, date, time) VALUES (?, ?, ?, ?)", id, request.name(), request.date(), request.time());

        ReservationResponse response = reservationMapper.mapToResponse(reservation);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);

        return ResponseEntity.ok().build();
    }
}
