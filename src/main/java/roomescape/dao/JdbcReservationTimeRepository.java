package roomescape.dao;

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
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                LocalTime.parse(rs.getString("start_at"))
        ));
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";

        List<ReservationTime> times = jdbcTemplate.query(sql, (rs, ronNum) -> new ReservationTime(
                rs.getLong("id"),
                LocalTime.parse(rs.getString("start_at"))
        ), id);

        return times.stream().findFirst();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", reservationTime.getStartAt().toString());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new ReservationTime(
                key.longValue(),
                reservationTime.getStartAt()
        );
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
