package roomescape.time.dao;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.TimeRequest;

@Repository
public class ReservationTimeJdbcDao implements ReservationTimeDao{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeJdbcDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
        return reservationTime;
    };

    @Override
    public List<ReservationTime> findAllTimes() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(
                sql,
                reservationTimeRowMapper
        );
    }

    @Override
    public ReservationTime insertTime(final TimeRequest timeRequest) {
        Map<String, Object> parameters = Map.of(
                "start_at", Time.valueOf(timeRequest.startAt())
        );

        Number insertedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new ReservationTime(insertedId.longValue(), timeRequest.startAt());
    }

    @Override
    public void deleteTime(final Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public ReservationTime findReservationTimeById(final Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                reservationTimeRowMapper,
                id
        );
    }
}
