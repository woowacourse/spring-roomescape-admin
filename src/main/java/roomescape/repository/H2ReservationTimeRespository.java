package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Primary
@Repository
public class H2ReservationTimeRespository implements ReservationTimeRepository {

    private static final RowMapper<ReservationTime> ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final LocalTime startAt = rs.getTime("start_at").toLocalTime();
        return new ReservationTime(id, startAt);
    };

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationTimeRespository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        final String sql = """
                SELECT * FROM reservation_time
                """;

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public Optional<ReservationTime> findById(final Long id) {
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
    public long add(final ReservationTime reservationTime) {
        final Number id = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(Map.of(
                        "start_at", reservationTime.getStartAt()
                ));
        return id.longValue();
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = """
                delete from reservation_time
                where id = ?
                """;
        jdbcTemplate.update(sql, id);
    }
}
