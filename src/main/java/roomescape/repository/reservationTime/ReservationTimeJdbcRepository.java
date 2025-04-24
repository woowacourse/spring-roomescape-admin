package roomescape.repository.reservationTime;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeJdbcRepository implements ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime add(ReservationTime time) {
        Map<String, LocalTime> params = new HashMap<>();
        params.put("start_at", time.getStartAt());

        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return new ReservationTime(id, time.getStartAt());
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_time";
        List<ReservationTime> times = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime time = new ReservationTime(
                            resultSet.getLong("id"),
                            resultSet.getTime("start_at").toLocalTime()
                    );
                    return time;
                }
        );
        return times;
    }

    @Override
    public int deleteBy(Long id) {
        String sql = "delete from reservation_time where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<ReservationTime> findBy(Long timeId) {
        String sql = "select * from reservation_time where id = ?";
        List<ReservationTime> time = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) ->
                        new ReservationTime(
                                resultSet.getLong("id"),
                                resultSet.getTime("start_at").toLocalTime()
                        ),
                timeId
        );
        return time.stream().findFirst();
    }
}
