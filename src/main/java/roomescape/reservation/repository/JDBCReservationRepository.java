package roomescape.reservation.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.entity.ReservationEntity;
import roomescape.reservationtime.entity.ReservationTimeEntity;
import roomescape.reservationtime.repository.ReservationTimeIdCache;

@Repository
@Primary
public class JDBCReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ReservationIdCache reservationIdCache;
    private final ReservationTimeIdCache reservationTimeIdCache;

    public JDBCReservationRepository(final JdbcTemplate jdbcTemplate, final ReservationIdCache reservationIdCache,
                                     final ReservationTimeIdCache reservationTimeIdCache) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationIdCache = reservationIdCache;
        this.reservationTimeIdCache = reservationTimeIdCache;
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
                    reservationIdCache.cacheId(reservation, entity.id());
                    reservationTimeIdCache.cacheId(reservation.getTime(), timeEntity.id());
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
                        reservationTimeIdCache.getCachedId(reservation.getTime()))).longValue();

        cacheId(reservation, generatedId);
        return reservation;
    }

    @Override
    public boolean deleteById(final long id) {
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id) != 0;
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
                        + "inner join reservation_time as t "
                        + "on r.time_id = t.id "
                        + "WHERE r.id = ?",
                (resultSet, rowNum) -> {
                    ReservationTimeEntity timeEntity = new ReservationTimeEntity(
                            resultSet.getLong("time_id"),
                            resultSet.getString("time_value"));

                    return new ReservationEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            timeEntity
                    );
                },
                id
        );
        return Optional.ofNullable(reservationEntity)
                .map(ReservationEntity::toReservation);
    }

    @Override
    public Long getCachedId(final Reservation reservation) {
        return reservationIdCache.getCachedId(reservation);
    }

    @Override
    public void cacheId(final Reservation reservation, final Long id) {
        reservationIdCache.cacheId(reservation, id);
    }
}
