package roomescape.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.controller.request.ReservationRequest;
import roomescape.reservation.controller.response.ReservationResponse;
import roomescape.reservationTime.controller.response.ReservationTimeResponse;

@Repository
public class H2ReservationRepository implements ReservationRepository {

    private static final RowMapper<ReservationResponse> ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong("reservation_id");
        final String name = rs.getString("name");
        final LocalDate date = rs.getDate("date").toLocalDate();
        final long timeId = rs.getLong("time_id");
        final LocalTime time = rs.getTime("time_value").toLocalTime();
        return new ReservationResponse(id, name, date, new ReservationTimeResponse(timeId, time));
    };

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationResponse> findAll() {
        String sql = """
                    SELECT
                        r.id as reservation_id,
                        r.name,
                        r.date,
                        t.id as time_id,
                        t.start_at as time_value
                    FROM reservation as r
                    inner join reservation_time as t
                    on r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public ReservationResponse findById(final Long id) {
        String sql = """
                    SELECT
                        r.id as reservation_id,
                        r.name,
                        r.date,
                        t.id as time_id,
                        t.start_at as time_value
                    FROM reservation as r
                    inner join reservation_time as t
                    on r.time_id = t.id
                    where r.id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public long add(final ReservationRequest request) {
        final Number id = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(
                        Map.of(
                                "name", request.name(),
                                "date", request.date(),
                                "time_id", request.timeId()
                        )
                );
        return id.longValue();
    }

    @Override
    public void deleteById(final Long id) {
        if (findById(id) == null) {
            throw new NoSuchElementException("해당하는 id의 예약기록이 없습니다.");
        }
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
