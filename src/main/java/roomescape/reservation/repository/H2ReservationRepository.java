package roomescape.reservation.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.common.repository.AbstractRepository;
import roomescape.common.repository.IdCache;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.entity.ReservationEntity;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.entity.ReservationTimeEntity;

@Repository
@Primary
public class H2ReservationRepository implements AbstractRepository<Reservation> {

    private final JdbcTemplate jdbcTemplate;
    private final IdCache<Reservation> cache;
    private final IdCache<ReservationTime> timeCache;

    public H2ReservationRepository(final JdbcTemplate jdbcTemplate, final IdCache<Reservation> cache,
                                   final IdCache<ReservationTime> timeCache) {
        this.jdbcTemplate = jdbcTemplate;
        this.cache = cache;
        this.timeCache = timeCache;
    }

    @Override
    public List<Reservation> getAll() {
        return jdbcTemplate.query(
                "SELECT "
                        + "r.id as reservation_id, "
                        + "r.name, "
                        + "r.date, "
                        + "t.id as time_id, "
                        + "t.start_at as time_value "
                        + "FROM reservation as r "
                        + "inner join reservation_time as t "
                        + "on r.time_id = t.id",
                (resultSet, rowNum) -> {
                    ReservationTimeEntity timeEntity = new ReservationTimeEntity(
                            resultSet.getLong("time_id"),
                            resultSet.getString("time_value"));

                    ReservationEntity entity = new ReservationEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            timeEntity
                    );
                    Reservation reservation = entity.toReservation();
                    cacheId(reservation, entity.id());
                    timeCache.cacheId(reservation.getTime(), timeEntity.id());
                    return reservation;
                }
        );
    }

    public Reservation put(final Reservation reservation) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        long generatedId = simpleJdbcInsert.executeAndReturnKey(
                Map.of("name", reservation.getName(), "date", reservation.getDate(), "time_id",
                        timeCache.getCachedId(reservation.getTime()))).longValue();

        cacheId(reservation, generatedId);
        return reservation;
    }

    @Override
    public void deleteById(final long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

    @Override
    public Optional<Reservation> findById(final long id) {
        ReservationEntity reservationEntity = jdbcTemplate.queryForObject(
                "SELECT "
                        + "r.id as reservation_id, "
                        + "r.name, "
                        + "r.date, "
                        + "t.id as time_id, "
                        + "t.start_at as time_value "
                        + "FROM reservation as r "
                        + "WHERE r.id = ?"
                        + "inner join reservation_time as t "
                        + "on r.time_id = t.id",
                (resultSet, rowNum) -> {
                    ReservationTimeEntity timeEntity = new ReservationTimeEntity(
                            resultSet.getLong("time_id"),
                            resultSet.getString("time_value"));

                    ReservationEntity entity = new ReservationEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            timeEntity
                    );
                    return entity;
                }
                , id
        );
        return Optional.ofNullable(reservationEntity)
                .map(ReservationEntity::toReservation);
    }

    @Override
    public Long getCachedId(final Reservation reservation) {
        return cache.getCachedId(reservation);
    }

    @Override
    public void cacheId(final Reservation reservation, final Long id) {
        cache.cacheId(reservation, id);
    }
}
