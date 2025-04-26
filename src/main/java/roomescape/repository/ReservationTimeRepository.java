package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private static final String DUPLICATE_TIME_EXCEPTION_MESSAGE = "해당 시간대는 이미 추가되어 있습니다.";
    private static final String INVALID_ID_EXCEPTION_MESSAGE = "해당 아이디는 존재하지 않습니다.";
    private static final int EMPTY_ROW_COUNT = 0;

    private final RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"),
            LocalTime.parse(resultSet.getString("start_at"))
    );
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> readAllReservationTimes() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public synchronized ReservationTime createReservationTime(final ReservationTime reservationTime) {
        checkDuplicates(reservationTime);

        Number id = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(
                        Map.of(
                                "start_at", reservationTime.getStartAt()
                        )
                );

        return new ReservationTime(id.longValue(), reservationTime.getStartAt());
    }

    public synchronized void deleteReservationTimeById(final Long id) {
        String sql = "delete from reservation_time where id = ?";
        int rowCount = jdbcTemplate.update(sql, id);

        if (rowCount == EMPTY_ROW_COUNT) {
            throw new IllegalArgumentException(INVALID_ID_EXCEPTION_MESSAGE);
        }
    }

    private void checkDuplicates(final ReservationTime reservationTime) {
        List<ReservationTime> reservationTimes = readAllReservationTimes();

        boolean isDuplicate = reservationTimes.stream()
                .anyMatch(reserveTime ->
                        reserveTime.getStartAt().equals(reservationTime.getStartAt())
                );

        if (isDuplicate) {
            throw new IllegalArgumentException(DUPLICATE_TIME_EXCEPTION_MESSAGE);
        }
    }
}
