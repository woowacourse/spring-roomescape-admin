package roomescape;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private static final String FIND_TIME_BY_ID = """
            SELECT id, start_at
            FROM reservation_time
            WHERE id = ?
            """;
    private static final String FIND_RESERVATION_BY_ID = """
            SELECT
            r.id AS reservation_id,
            r.name AS reservation_name,
            r.date AS reservation_date,
            t.id AS time_id,
            t.start_at AS time_start_at
            FROM reservation r
            JOIN reservation_time t ON r.time_id = t.id
            WHERE r.id = ?
            """;
    private static final String FIND_ALL_RESERVATION = """
            SELECT
                r.id AS reservation_id,
                r.name AS reservation_name,
                r.date AS reservation_date,
                t.id AS time_id,
                t.start_at AS time_start_at
            FROM reservation r
            JOIN reservation_time t ON r.time_id = t.id
            ORDER BY r.id
            """;
    private static final String INSERT_RESERVATION = """
            INSERT INTO reservation (name, date, time)
            VALUES (?, ?, ?)
            """;
    private static final String DELETE_RESERVATION_BY_ID = """
            DELETE FROM reservation
            WHERE id = ?
            """;

    private final JdbcTemplate jdbcTemplate;


    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        final List<Reservation> reservations = jdbcTemplate.query(FIND_ALL_RESERVATION, this::mapToReservation);
        return ResponseEntity.ok(ReservationResponse.from(reservations));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @RequestBody ReservationCreateRequest request
    ) {
        final ReservationTime time = findTimeBy(request.timeId());
        final Reservation reservationData = Reservation.create(request.toData(time));

        final long reservationId = insertReservation(reservationData);
        final Reservation newReservation = findReservationBy(reservationId);

        return ResponseEntity.ok(ReservationResponse.from(newReservation));
    }

    @DeleteMapping("/{reservation-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("reservation-id") Long reservationId
    ) {
        jdbcTemplate.update(DELETE_RESERVATION_BY_ID, reservationId);
        return ResponseEntity.ok(null);
    }


    private long insertReservation(final Reservation reservation) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_RESERVATION,
                    Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate().toString());
            preparedStatement.setString(3, reservation.getTime().toString());

            return preparedStatement;
        }, keyHolder);

        return generatedIdFrom(keyHolder);
    }

    private long generatedIdFrom(final KeyHolder keyHolder) {
        if (keyHolder.getKey() == null) {
            throw new IllegalStateException("생성된 id를 가져오지 못했습니다.");
        }

        return keyHolder.getKey().longValue();
    }


    private Reservation findReservationBy(final long reservationId) {
        return jdbcTemplate.queryForObject(
                FIND_RESERVATION_BY_ID,
                this::mapToReservation,
                reservationId
        );
    }

    private ReservationTime findTimeBy(final long timeId) {
        return jdbcTemplate.queryForObject(
                FIND_TIME_BY_ID,
                this::mapToTime,
                timeId
        );
    }


    private Reservation mapToReservation(ResultSet resultSet, int rowNum) throws SQLException {
        return Reservation.restore(
                resultSet.getLong("reservation_id"),
                resultSet.getString("reservation_name"),
                resultSet.getDate("reservation_date").toLocalDate(),
                ReservationTime.restore(
                        resultSet.getLong("time_id"),
                        resultSet.getTime("time_start_at").toLocalTime()
                )
        );
    }

    private ReservationTime mapToTime(ResultSet resultSet, int rowNum) throws SQLException {
        return ReservationTime.restore(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
    }
}
