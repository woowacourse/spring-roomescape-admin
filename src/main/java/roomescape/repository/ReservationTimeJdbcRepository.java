package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationTimeJdbcRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> {
        final ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
        );
        return reservationTime;
    };

    public ReservationTimeJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(final ReservationTime reservationTime) {
        return jdbcInsert.executeAndReturnKey(Map.of("start_at", reservationTime.getStartAt())).longValue();
    }

    @Override
    public List<ReservationTime> findAll() {
        final String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ReservationTime findById(final Long id) {
        final String sql = "select * from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
