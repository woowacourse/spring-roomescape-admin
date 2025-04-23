package roomescape.persist.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationDateTimeFormatter;
import roomescape.persist.entity.ReservationTimeEntity;
import roomescape.domain.ReservationTime;

@Repository
public class H2ReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        RowMapper<ReservationTimeEntity> rowMapper = (rs, rowNum) -> new ReservationTimeEntity(
                rs.getLong("id"),
                rs.getString("start_at")
        );
        List<ReservationTimeEntity> reservationTimeEntities = jdbcTemplate.query(sql, rowMapper);
        return reservationTimeEntities.stream()
                .map(ReservationTimeEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<ReservationTime> findById(long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        try {
            RowMapper<ReservationTimeEntity> rowMapper = (rs, rowNum) -> new ReservationTimeEntity(
                    rs.getLong("id"),
                    rs.getString("start_at")
            );
            ReservationTimeEntity entity = jdbcTemplate.queryForObject(sql, rowMapper, id);
            assert entity != null;
            return Optional.of(entity.toDomain());
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public ReservationTime add(ReservationTime reservationTime) {
        ReservationTimeEntity reservationTimeEntity = ReservationTimeEntity.fromDomain(reservationTime);
        Map<String, Object> params = new HashMap<>();
        params.put("start_at", reservationTimeEntity.getStartAt());
        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        ReservationTimeEntity savedReservationTimeEntity = reservationTimeEntity.copyWithId(id);
        return savedReservationTimeEntity.toDomain();
    }

    @Override
    public void removeById(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByStartTime(LocalTime startAt) {
        String sql = "SELECT EXISTS (SELECT 1 FROM reservation_time WHERE start_at = ?)";
        String formattedStartAt = ReservationDateTimeFormatter.formatTime(startAt);
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, formattedStartAt);
        return Boolean.TRUE.equals(exists);
    }
}
