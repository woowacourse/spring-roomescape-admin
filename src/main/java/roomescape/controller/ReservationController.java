package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.entity.Reservation;

@RestController
public class ReservationController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/reservations")
    public List<Reservation> readAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getObject("date", LocalDate.class),
                        resultSet.getObject("time", LocalTime.class)
                )
        );
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequestDto reservationRequestDto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        long generatedKey = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "name", reservationRequestDto.name(),
                "date", reservationRequestDto.date(),
                "time", reservationRequestDto.time()
        )).longValue();

        Reservation reservation = new Reservation(
                generatedKey,
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                reservationRequestDto.time()
        );
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }
}
