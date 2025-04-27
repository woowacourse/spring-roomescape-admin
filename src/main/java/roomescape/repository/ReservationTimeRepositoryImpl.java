package roomescape.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.exception.ResourceNotFoundException;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        Map<String, Object> parameters = new HashMap<>();
        LocalTime startAt = reservationTime.getStartAt();
        parameters.put("start_at", startAt);
        long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new ReservationTime(id, startAt);
    }

    @Override
    public List<ReservationTime> findAll() {
        final String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> {
            long id = resultSet.getLong("id");
            LocalTime startAt = LocalTime.parse(resultSet.getString("start_at"));
            return new ReservationTime(id, startAt);
        });
    }

    @Override
    public void delete(final long id) {
        final String sql = "delete from reservation_time where id = ?";
        int updatedRow = jdbcTemplate.update(sql, id);
        if (updatedRow != 1) {
            throw new ResourceNotFoundException("[ERROR] 해당 id가 존재하지 않습니다.");
        }
    }
}
