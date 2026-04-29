package roomescape;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.TimeCreateRequest;
import roomescape.dto.response.TimeResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
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
            INSERT INTO reservation_time (id, start_at)
            VALUES (?, ?)
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

    private ReservationTime mapToTime(ResultSet resultSet, int rowNum) throws SQLException {
        return ReservationTime.restore(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
    }
}
