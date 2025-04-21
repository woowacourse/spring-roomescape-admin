package roomescape.infra;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationTimeDatabase {

    private static final RowMapper<ReservationTime> ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final LocalTime startTime = rs.getTime("start_at").toLocalTime();
        return new ReservationTime(id, startTime);
    };

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDatabase(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        final String sql = """
                SELECT * FROM RESERVATION_TIME
                """;

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public ReservationTime findById(final long id) {
        final String sql = """
                SELECT * FROM RESERVATION_TIME
                WHERE id = ?
                """;

        return jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
    }

    public long saveAndGetId(final ReservationTimeCreateRequest request) {
        final Number savedId = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(request.dataMap());

        return savedId.longValue();
    }

    public void deleteById(final long id) {
        final String sql = """
                DELETE FROM RESERVATION_TIME
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}
