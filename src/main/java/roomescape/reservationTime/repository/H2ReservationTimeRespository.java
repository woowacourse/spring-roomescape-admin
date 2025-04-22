package roomescape.reservationTime.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservationTime.controller.request.ReservationTimeRequest;
import roomescape.reservationTime.controller.response.ReservationTimeResponse;


@Repository
public class H2ReservationTimeRespository implements ReservationTimeRepository {

    private static final RowMapper<ReservationTimeResponse> ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final LocalTime startAt = rs.getTime("start_at").toLocalTime();
        return new ReservationTimeResponse(id, startAt);
    };

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationTimeRespository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTimeResponse> findAll() {
        final String sql = """
                SELECT * FROM reservation_time
                """;

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public ReservationTimeResponse findById(final Long id) {
        final String sql = """
                SELECT * FROM RESERVATION_TIME
                WHERE id = ?
                """;

        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public long add(final ReservationTimeRequest request) {
        final Number id = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(Map.of(
                        "start_at", request.startAt()
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
