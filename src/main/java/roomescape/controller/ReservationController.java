package roomescape.controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
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
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAllReservation() {
        String sql = """                                                                                                                                                                                     
              SELECT r.id       AS reservation_id,
                     r.name     AS reservation_name,
                     r.date     AS reservation_date,
                     t.id       AS time_id,
                     t.start_at AS time_start_at
              FROM reservation r
                  INNER JOIN reservation_time t ON r.time_id = t.id
              """;
        List<ReservationResponseDto> responseDtoList = jdbcTemplate.query(sql,(resultSet, rowNum) -> {
            ReservationTime time = ReservationTime.builder()
                    .id(resultSet.getLong("time_id"))
                    .startAt(resultSet.getObject("time_start_at", LocalTime.class))
                    .build();

            return new ReservationResponseDto(
                    resultSet.getLong("reservation_id"),
                    resultSet.getString("reservation_name"),
                    resultSet.getObject("reservation_date",LocalDate.class),
                    time);
        });
        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        ReservationTime time = jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                (resultSet, rowNum) -> ReservationTime.builder()
                                .id(resultSet.getLong("id"))
                                .startAt(resultSet.getObject("start_at", LocalTime.class))
                                .build(),
                reservationRequestDto.timeId()
        );
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationRequestDto.name());
            ps.setDate(2, Date.valueOf(reservationRequestDto.date()));
            ps.setLong(3, time.getId());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();

        Reservation newReservation = reservationRequestDto.toEntity(time);
        newReservation.assignId(generatedId);
        return ResponseEntity.ok(ReservationResponseDto.from(newReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);

        if (affectedRows == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
