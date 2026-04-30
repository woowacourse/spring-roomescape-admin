package roomescape.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@Repository
public class JdbcReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT r.id AS r_id, r.name, r.date, t.id AS t_id, t.start_at " +
                "FROM reservation r INNER JOIN reservation_time t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, rowMapper());
    }

    @Override
    public Reservation findById(long reservationId) {
        String sql = "SELECT r.id AS r_id, r.name, r.date, t.id AS t_id, t.start_at " +
                "FROM reservation r INNER JOIN reservation_time t ON r.time_id = t.id " +
                "WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper(), reservationId);
    }

    @Override
    public long insert(ReservationRequest request) {
        SimpleJdbcInsert insert = createInsert();
        Map<String, Object> params = createParams(request);
        return insert.executeAndReturnKey(params).longValue();
    }

    private SimpleJdbcInsert createInsert() {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private Map<String, Object> createParams(ReservationRequest request) {
        return Map.of("name", request.name(), "date", request.date(), "time_id", request.timeId());
    }

    @Override
    public void deleteById(long reservationId) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, reservationId);
    }

    private RowMapper<Reservation> rowMapper() {
        return (rs, rowNum) -> new Reservation(
                rs.getLong("r_id"),
                rs.getString("name"),
                rs.getString("date"),
                new ReservationTime(rs.getLong("t_id"), rs.getString("start_at"))
        );
    }
}
