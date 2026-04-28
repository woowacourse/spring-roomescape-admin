package roomescape.controller;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateDto;
import roomescape.dto.ReservationDto;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@RestController()
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> findAllReservations() {
        List<ReservationDto> result = jdbcTemplate.query(
                """
                        SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at as time_value
                        FROM reservation r
                        JOIN reservation_time t ON r.time_id = t.id
                        """,
                (rs, rowNum) -> ReservationDto.from(
                        Reservation.builder()
                                .id(rs.getLong("id"))
                                .name(rs.getString("name"))
                                .date(LocalDate.parse(rs.getString("date")))
                                .time(ReservationTime.builder()
                                        .id(rs.getLong("time_id"))
                                        .startAt(LocalTime.parse(rs.getString("time_value")))
                                        .build())
                                .build()
                )
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(
            @RequestBody ReservationCreateDto request
    ) {
        Long reservationTimeId = request.timeId();

        ReservationTime reservationTime = jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                (rs, rowNum) -> ReservationTime.builder()
                        .id(rs.getLong("id"))
                        .startAt(LocalTime.parse(rs.getString("start_at")))
                        .build(),
                reservationTimeId
        );

        Reservation reservation = request.toEntity(reservationTime);

        String formattedDate = reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, formattedDate);
            ps.setLong(3, reservation.getTimeId());
            return ps;
        }, keyHolder);

        Long saveId = keyHolder.getKey().longValue();
        Reservation saved = Reservation.builder()
                .id(saveId)
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservationTime)
                .build();

        return ResponseEntity.ok(ReservationDto.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        return ResponseEntity.ok().build();
    }
}
