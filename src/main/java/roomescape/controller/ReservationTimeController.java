package roomescape.controller;

import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.entity.ReservationTime;

@RestController
public class ReservationTimeController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(
            @RequestBody ReservationTimeRequestDto reservationTimeRequestDto
    ) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
        long generatedKey = simpleJdbcInsert
                .executeAndReturnKey(new BeanPropertySqlParameterSource(reservationTimeRequestDto))
                .longValue();

        ReservationTime reservationTime = new ReservationTime(generatedKey, reservationTimeRequestDto.startAt());
        return ResponseEntity.ok(reservationTime);
    }

    @GetMapping("/times")
    public List<ReservationTime> readAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getObject("start_at", LocalTime.class)
                )
        );
    }

    @DeleteMapping("times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }
}
