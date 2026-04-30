package roomescape.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Repository
public class JdbcReservationTimeDao implements ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, rowMapper());
    }

    @Override
    public long insert(ReservationTimeRequest reservationTimeRequest) {
        SimpleJdbcInsert insert = createInsert();
        Map<String, Object> params = createParams(reservationTimeRequest);
        return insert.executeAndReturnKey(params).longValue();
    }

    @Override
    public void deleteById(long timeId) {
        String sql = "DELETE FROM reservation_time where id = ?";
        jdbcTemplate.update(sql, timeId);
    }

    @Override
    public ReservationTime findById(long timeId) {
        String sql = "SELECT id, start_at FROM reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper(), timeId);
    }

    private RowMapper<ReservationTime> rowMapper() {
        return (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getString("start_at")
        );
    }

    private SimpleJdbcInsert createInsert() {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    private Map<String, Object> createParams(ReservationTimeRequest request) {
        return Map.of("start_at", request.startAt());
    }
}
