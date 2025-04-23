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

@Repository
@Primary
public class H2ReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ReservationIdCache cache;

    public H2ReservationRepository(final JdbcTemplate jdbcTemplate, final ReservationIdCache cache) {
        this.jdbcTemplate = jdbcTemplate;
        this.cache = cache;
    }

    @Override
    public List<Reservation> getAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                (resultSet, rowNum) -> {
                    ReservationEntity entity = new ReservationEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );
                    Reservation reservation = entity.toReservation();
                    cache.put(reservation, entity.id());
                    return reservation;
                }
        );
    }

    public Reservation put(final Reservation reservation) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        long generatedId = simpleJdbcInsert.executeAndReturnKey(
                Map.of("name", reservation.getName(), "date", reservation.getDate(), "time",
                        reservation.getTime())).longValue();

        cache.put(reservation, generatedId);
        return reservation;
    }

    @Override
    public void deleteById(final long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

    @Override
    public Optional<Reservation> findById(final long id) {
        ReservationEntity reservationEntity = jdbcTemplate.queryForObject(
                "SELECT id, name, date, time FROM reservation",
                (resultSet, rowNum) -> new ReservationEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                )
        );
        return Optional.ofNullable(reservationEntity)
                .map(ReservationEntity::toReservation);
    }
}
