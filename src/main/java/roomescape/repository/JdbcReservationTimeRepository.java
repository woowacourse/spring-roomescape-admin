package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Long id = resultSet.getLong("id");
            LocalTime startAt = resultSet.getObject("start_at", LocalTime.class);
            ReservationTime reservationTime = new ReservationTime(startAt);
            return reservationTime.toEntity(id);
        });
    }

    @Override
    public boolean existByStartAt(LocalTime startAt) {
        String sql = "SELECT EXISTS(SELECT 1 FROM reservation_time WHERE start_at = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, startAt);
    }

    @Override
    public ReservationTime insertAndReturn(ReservationTime reservationTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("start_at", reservationTime.getStartAt());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return reservationTime.toEntity(id);
    }

    @Override
    public int deleteByIdAndCountAffected(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public ReservationTime findById(Long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    (resultSet, rowNum) -> {
                        Long timeId = resultSet.getLong("id");
                        LocalTime startAt = resultSet.getObject("start_at", LocalTime.class);
                        ReservationTime reservationTime = new ReservationTime(startAt);
                        return reservationTime.toEntity(timeId);
                    },
                    id);
        } catch (EmptyResultDataAccessException exception) {
            throw new IllegalArgumentException("존재하지 않는 예약시간의 id입니다.");
        }
    }
}
