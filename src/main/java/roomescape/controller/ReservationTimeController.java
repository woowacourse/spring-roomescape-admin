package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTime;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeController(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> getReservationTimes() {
        final List<ReservationTimeResponseDto> reservationTimeResponseDtos = jdbcTemplate.query(
                        "SELECT id, start_at FROM reservation_time",
                        (resultSet, rowNum) -> new ReservationTime(
                                resultSet.getLong("id"),
                                resultSet.getTime("start_at").toLocalTime()))
                .stream()
                .map(ReservationTimeResponseDto::from)
                .toList();

        return new ResponseEntity<>(reservationTimeResponseDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> add(@RequestBody final ReservationTimeRequestDto reservationTimeRequestDto) {
        final Map<String, Object> args = Map.of(
                "start_at", reservationTimeRequestDto.startAt());

        final long generatedKey = simpleJdbcInsert.executeAndReturnKey(args).longValue();
        final ReservationTime reservationTime = new ReservationTime(
                generatedKey,
                reservationTimeRequestDto.startAt());

        return new ResponseEntity<>(ReservationTimeResponseDto.from(reservationTime), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        final String sql = "DELETE FROM reservation_time WHERE id = :id";
        final SqlParameterSource parameters = new MapSqlParameterSource("id", id);

        jdbcTemplate.update(sql, parameters);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
