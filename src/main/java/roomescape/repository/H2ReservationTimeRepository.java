package roomescape.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class H2ReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertReservationTime;
    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
        );
        return reservationTime;
    };

    public H2ReservationTimeRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertReservationTime = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("reservation_time")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime add(final ReservationTime reservationTime) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("start_at", reservationTime.getStartAt());

        Number newId = insertReservationTime.executeAndReturnKey(parameters);

        return findById(newId.longValue());
    }

    @Override
    public ReservationTime findById(final Long id) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, reservationTimeRowMapper, id);
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, reservationTimeRowMapper);
    }

    @Override
    public void removeById(final Long id) {
    }
}
