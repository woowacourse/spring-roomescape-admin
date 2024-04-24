package roomescape.dao;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.ReservationTimeAddRequest;
import roomescape.domain.ReservationTime;

@Repository
public class H2TimeDao implements TimeDao {
    public static final String ID_COLUMN_NAME = "id";
    public static final String START_TIME_COLUMN_NAME = "start_at";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns(ID_COLUMN_NAME)
                .usingColumns(START_TIME_COLUMN_NAME);
    }

    private final RowMapper<ReservationTime> rowMapper = (rs, rowNum) -> new ReservationTime(
            rs.getLong(ID_COLUMN_NAME),
            rs.getString(START_TIME_COLUMN_NAME)
    );

    @Override
    public ReservationTime add(ReservationTimeAddRequest request) {
        Map<String, Object> parameters = Map.of(
                START_TIME_COLUMN_NAME, request.startTime()
        );
        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new ReservationTime(key.longValue(), request.startTime());
    }

    @Override
    public boolean isExist(LocalTime time) {
        String sql = "SELECT * FROM reservation_time WHERE " + START_TIME_COLUMN_NAME + " = ? LIMIT 1";
        List<ReservationTime> reservationTime = jdbcTemplate.query(sql, rowMapper, time);
        return !reservationTime.isEmpty();
    }
}
