package roomescape.reservationtime.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.entity.ReservationTimeEntity;

@Repository
@Primary
public class H2ReservationTimeRepository implements ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ReservationTimeIdCache cache;

    public H2ReservationTimeRepository(final JdbcTemplate jdbcTemplate,
                                       final ReservationTimeIdCache reservationTimeIdCache) {
        this.jdbcTemplate = jdbcTemplate;
        this.cache = reservationTimeIdCache;
    }

    @Override
    public List<ReservationTime> getAll() {
        return jdbcTemplate.query(
                "SELECT id, start_at FROM reservation_time",
                (resultSet, rowNum) -> {
                    ReservationTimeEntity entity = new ReservationTimeEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("start_at")
                    );
                    ReservationTime reservationTime = entity.toReservationTime();
                    cacheId(reservationTime, entity.id());
                    return reservationTime;
                }
        );
    }

    @Override
    public ReservationTime put(final ReservationTime reservationTime) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");

        long generatedId = simpleJdbcInsert.executeAndReturnKey(
                Map.of("start_at", reservationTime.getStartAt())).longValue();

        cacheId(reservationTime, generatedId);
        return reservationTime;
    }

    @Override
    public int deleteById(final long id) {
        return jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }

    @Override
    public Optional<ReservationTime> findById(final long id) {
        ReservationTimeEntity reservationTimeEntity = jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                (resultSet, rowNum) -> new ReservationTimeEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                ),
                id
        );
        return Optional.ofNullable(reservationTimeEntity)
                .map(ReservationTimeEntity::toReservationTime);
    }

    @Override
    public boolean checkExistsByStartAt(final LocalTime time) {
        Boolean exists = jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM reservation_time WHERE start_at = ?)",
                Boolean.class,
                time
        );
        return Boolean.TRUE.equals(exists);
    }

    @Override
    public Long getCachedId(final ReservationTime reservationTime) {
        return cache.getCachedId(reservationTime);
    }

    @Override
    public void cacheId(final ReservationTime reservationTime, final Long id) {
        cache.cacheId(reservationTime, id);
    }
}
