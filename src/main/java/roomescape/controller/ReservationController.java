package roomescape.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.Reservation;

import javax.sql.DataSource;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationController(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("reservation")
            .usingColumns("name", "date", "time_id")
            .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        final List<ReservationResponseDto> reservationResponseDtos = jdbcTemplate.query(
            """
                SELECT
                r.id as reservation_id,
                r.name as name,
                r.date as date_value,
                t.id as time_id,
                t.start_at as time_value
                FROM reservation as r
                INNER JOIN reservation_time as t
                ON r.time_id = t.id""",
            (resultSet, rowNum) -> new ReservationResponseDto(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getDate("date_value").toLocalDate(),
                new ReservationTimeResponseDto(
                    resultSet.getLong("time_id"),
                    resultSet.getTime("time_value").toLocalTime())
            ));

        return new ResponseEntity<>(reservationResponseDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> add(
        @RequestBody final ReservationRequestDto reservationRequestDto) {
        final Map<String, Object> args = Map.of(
            "name", reservationRequestDto.name(),
            "date", reservationRequestDto.date(),
            "time_id", reservationRequestDto.timeId());

        final long generatedKey = simpleJdbcInsert.executeAndReturnKey(args).longValue();
        final Reservation reservation = new Reservation(
            generatedKey,
            reservationRequestDto.name(),
            reservationRequestDto.date(),
            reservationRequestDto.timeId());
        final String sql = "SELECT start_at FROM reservation_time WHERE id = :id";
        final SqlParameterSource parameters = new MapSqlParameterSource("id",
            reservationRequestDto.timeId());

        final ReservationTimeResponseDto reservationTimeResponseDto =
            jdbcTemplate.queryForObject(sql, parameters,
                (resultSet, rowNum) -> new ReservationTimeResponseDto(
                    reservationRequestDto.timeId(),
                    resultSet.getTime("start_at").toLocalTime()
                ));

        return new ResponseEntity<>(ReservationResponseDto.from(reservation, reservationTimeResponseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        final String sql = "DELETE FROM reservations WHERE id = :id";
        final SqlParameterSource parameters = new MapSqlParameterSource("id", id);

        jdbcTemplate.update(sql, parameters);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
