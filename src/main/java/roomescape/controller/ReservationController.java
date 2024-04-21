package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private final ConcurrentHashMap<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);
    private final JdbcTemplate jdbcTemplate;

    ReservationController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public List<ReservationResponseDto> reservations() {
        final String sql = "select id, name, date, time from reservation";
        final List<Reservation> reservationList = jdbcTemplate.query(sql, reservationRowMapper);
        return reservationList.stream()
                              .map(ReservationResponseDto::from)
                              .toList();
    }

    @GetMapping("/reservations/{id}")
    public ReservationResponseDto reservation(@PathVariable final Long id) {
        final String sql = "select id, name, date, time from reservation where id = ?";
        final Reservation reservation = jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
        return ReservationResponseDto.from(reservation);
    }

    @PostMapping("/reservations")
    public ReservationResponseDto create(@RequestBody final ReservationRequestDto request) {
        final Long id = index.getAndIncrement();
        final Reservation reservation = new Reservation(id, request.getName(), request.getDate(), request.getTime());
        reservations.put(id, reservation);
        return ReservationResponseDto.from(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        if (reservations.containsKey(id)) {
            reservations.remove(id);
            return ResponseEntity.noContent()
                                 .build();
        }
        return ResponseEntity.notFound()
                             .build();
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };
}
