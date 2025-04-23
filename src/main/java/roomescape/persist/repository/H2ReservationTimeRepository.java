package roomescape.persist.repository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
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
        List<ReservationTimeEntity> reservationTimeEntities = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new ReservationTimeEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                )
        );
        return reservationTimeEntities.stream()
                .map(ReservationTimeEntity::toDomain)
                .toList();
    }

    @Override
    public ReservationTime findById(long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        ReservationTimeEntity reservationTimeEntity = jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new ReservationTimeEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                ),
                id
        );
        return reservationTimeEntity.toDomain();
    }

    @Override
    public ReservationTime add(ReservationTime reservationTime) {
        ReservationTimeEntity reservationTimeEntity = ReservationTimeEntity.fromDomain(reservationTime);
        Map<String, String> params = new HashMap<>();
        params.put("start_at", reservationTimeEntity.getStartAt());
        long id = jdbcInsert.executeAndReturnKey(params).intValue();
        ReservationTimeEntity savedReservationTimeEntity = reservationTimeEntity.copyWithId(id);
        return savedReservationTimeEntity.toDomain();
    }

    @Override
    public void removeById(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByStartTime(LocalTime localTime) {
        String sql = "SELECT COUNT(*) FROM reservation_time WHERE start_at = ?";
        int count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                localTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        return count > 0;
    }
}
