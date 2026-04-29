package roomescape;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.TimeCreateRequest;
import roomescape.dto.response.TimeResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimeController {

    private static final String FIND_TIME_BY_ID = """
            SELECT id, start_at
            FROM reservation_time
            WHERE id = ?
            """;
    private static final String FIND_ALL_TIME = """
            SELECT id, start_at
            FROM reservation_time
            ORDER BY id
            """;
    private static final String INSERT_TIME = """
            INSERT INTO reservation_time (start_at)
            VALUES ?
            """;
    private static final String DELETE_TIME_BY_ID = """
            DELETE FROM reservation_time
            WHERE id = ?
            """;

    private final JdbcTemplate jdbcTemplate;


    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        final List<ReservationTime> times = jdbcTemplate.query(FIND_ALL_TIME, this::mapToTime);
        return ResponseEntity.ok(TimeResponse.from(times));
    }

    @PostMapping
    public ResponseEntity<TimeResponse> create(
            @RequestBody TimeCreateRequest request
    ) {
        final ReservationTime timeData = ReservationTime.create(request.toData());
        final long newTimeId = insertTime(timeData);
        final ReservationTime newTime = findTimeBy(newTimeId);

        return ResponseEntity.ok(TimeResponse.from(newTime));
    }

    @DeleteMapping("/{time-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("time-id") Long timeId
    ) {
        jdbcTemplate.update(DELETE_TIME_BY_ID, timeId);
        return ResponseEntity.ok(null);
    }


    private long insertTime(final ReservationTime reservationTime) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_TIME,
                    Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, reservationTime.getStartAt().toString());

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


    private ReservationTime findTimeBy(final long timeId) {
        return jdbcTemplate.queryForObject(
                FIND_TIME_BY_ID,
                this::mapToTime,
                timeId
        );
    }


    private ReservationTime mapToTime(ResultSet resultSet, int rowNum) throws SQLException {
        return ReservationTime.restore(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
    }
}
