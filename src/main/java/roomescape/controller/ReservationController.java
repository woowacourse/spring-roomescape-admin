package roomescape.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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
import roomescape.entity.Reservation;

import javax.sql.DataSource;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private static final String TABLE_NAME = "reservation";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationController(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingColumns("name", "date", "time")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        final List<ReservationResponseDto> reservationResponseDtos = jdbcTemplate.query(
                        String.format("SELECT id, name, date, time FROM %s", TABLE_NAME),
                        (resultSet, rowNum) -> new Reservation(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getDate("date").toLocalDate(),
                                resultSet.getTime("time").toLocalTime()))
                .stream()
                .map(ReservationResponseDto::from)
                .toList();

        return new ResponseEntity<>(reservationResponseDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> add(@RequestBody final ReservationRequestDto reservationRequestDto) {
        final Map<String, Object> args = Map.of(
                "name", reservationRequestDto.name(),
                "date", reservationRequestDto.date(),
                "time", reservationRequestDto.time());

        final long generatedKey = simpleJdbcInsert.executeAndReturnKey(args).longValue();
        final Reservation reservation = new Reservation(
                generatedKey,
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                reservationRequestDto.time());

        return new ResponseEntity<>(ReservationResponseDto.from(reservation), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        final String sql = String.format("DELETE FROM %s WHERE id = :id", TABLE_NAME);
        final SqlParameterSource parameters = new MapSqlParameterSource("id", id);

        jdbcTemplate.update(sql, parameters);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
