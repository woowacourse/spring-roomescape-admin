package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public long save(final ReservationTime time) {
        final Map<String, Object> reservationTime = new HashMap<>(1);
        reservationTime.put("start_at", time.getStartAt());
        final Number number = insertActor.executeAndReturnKey(reservationTime);
        return getGenerateId(number);
    }

    public List<ReservationTime> readAll() {
        final String sql = "select id, start_at from reservation_time";
        final RowMapper<ReservationTime> rowMapper = getRowMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteById(final Long id) {
        final String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public ReservationTime findById(final Long id) {
        final String sql = "select id, start_at from reservation_time where id =?";
        final RowMapper<ReservationTime> rowMapper = getRowMapper();
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    private RowMapper<ReservationTime> getRowMapper() {
        return (resultSet, rowNum) ->
                ReservationTime.from(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime());
    }

    private long getGenerateId(final Number number) {
        return number.longValue();
    }
}
