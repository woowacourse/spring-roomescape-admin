package roomescape.reservationtime.dto.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.common.repository.AbstractRepository;
import roomescape.common.repository.IdCache;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.entity.ReservationTimeEntity;

@Repository
public class H2ReservationTimeRepository implements AbstractRepository<ReservationTime> {
    private final JdbcTemplate jdbcTemplate;
    private final IdCache<ReservationTime> cache;

    public H2ReservationTimeRepository(final JdbcTemplate jdbcTemplate, final IdCache<ReservationTime> cache) {
        this.jdbcTemplate = jdbcTemplate;
        this.cache = cache;
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
    public void deleteById(final long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }

    @Override
    public Optional<ReservationTime> findById(final long id) {
        ReservationTimeEntity reservationTimeEntity = jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                (resultSet, rowNum) -> new ReservationTimeEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                )
                , id
        );
        return Optional.ofNullable(reservationTimeEntity)
                .map(ReservationTimeEntity::toReservationTime);
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
