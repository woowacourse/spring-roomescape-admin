package roomescape.domain.reservation.controller;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.reservation.request.ReservationCreateRequest;
import roomescape.domain.reservation.response.ReservationResponse;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationController {

    private static final String FIND_ALL_RESERVATIONS_QUERY = """
            SELECT * FROM reservation;
            """;

    private static final String SAVE_RESERVATION_QUERY = """
            INSERT INTO reservation(name, date, time) VALUES (?, ?, ?);
            """;

    private static final String DELETE_RESERVATION_BY_ID_QUERY = """
            DELETE FROM reservation
            WHERE id = ?
            """;

    private static final String RESERVATION_TIME_PATTERN = "HH:mm";

    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<Reservation> reservations = jdbcTemplate.query(FIND_ALL_RESERVATIONS_QUERY, reservationRowMapper());

        List<ReservationResponse> results = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(results);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("reservation이 null 입니다.");
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String reservationName = request.name();
        LocalDate reservationDate = request.date();
        LocalTime reservationTime = request.time();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SAVE_RESERVATION_QUERY,
                    Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, reservationName);
            preparedStatement.setString(2, reservationDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            preparedStatement.setString(3,
                    reservationTime.format(DateTimeFormatter.ofPattern(RESERVATION_TIME_PATTERN)));

            return preparedStatement;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("예약 저장 후 생성된 ID를 가져오지 못했습니다.");
        }

        Long generatedId = key.longValue();

        ReservationResponse response = new ReservationResponse(
                generatedId,
                reservationName,
                reservationDate,
                reservationTime
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, String.valueOf(response.id()))
                .body(response);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long reservationId) {
        jdbcTemplate.update(DELETE_RESERVATION_BY_ID_QUERY, reservationId);
        return ResponseEntity.ok().build();
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNumber) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        );
    }
}
