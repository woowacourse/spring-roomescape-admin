package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.domain.ReservationTime;

public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservation_time")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ReservationTime(rs.getLong("id"), rs.getTime("start_at").toLocalTime())
        );
    }

    @Override
    public ReservationTime add(ReservationTime reservationTime) {
        Map<String, Object> parameter = Map.of("start_at", reservationTime.getStartAt());
        Long newId = simpleJdbcInsert.executeAndReturnKey(parameter).longValue();
        return new ReservationTime(newId, reservationTime);
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";
        ReservationTime reservationTime = jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getTime("start_at").toLocalTime()
                ),
                id
        );
        return Optional.ofNullable(reservationTime);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
