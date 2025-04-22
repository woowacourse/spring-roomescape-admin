package roomescape.infra.jdbc;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.business.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.infra.ReservationTimeDatabase;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class ReservationTimeJdbcDatabase implements ReservationTimeDatabase {

    private static final RowMapper<ReservationTime> ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final LocalTime startTime = rs.getTime("start_at").toLocalTime();
        return new ReservationTime(id, startTime);
    };

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeJdbcDatabase(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        final String sql = """
                SELECT * FROM RESERVATION_TIME
                """;

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public Optional<ReservationTime> findById(final long id) {
        final String sql = """
                SELECT * FROM RESERVATION_TIME
                WHERE id = ?
                """;

        try {
            final ReservationTime reservationTime = jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
            return Optional.ofNullable(reservationTime);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public long saveAndGetId(final ReservationTimeCreateRequest request) {
        final Number savedId = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(request.dataMap());

        return savedId.longValue();
    }

    @Override
    public void deleteById(final long id) {
        final String sql = """
                DELETE FROM RESERVATION_TIME
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}
