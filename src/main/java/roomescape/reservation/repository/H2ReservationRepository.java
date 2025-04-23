package roomescape.reservation.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.entity.ReservationEntity;

@Repository
@Primary
public class H2ReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationEntity> getAll() {
        List<ReservationEntity> reservations = jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                (resultSet, rowNum) -> {
                    ReservationEntity reservation = new ReservationEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );
                    return reservation;
                }
        );
        return reservations;
    }

    @Override
    public ReservationEntity put(final Reservation reservation) {
        return null;
    }

    @Override
    public void deleteById(final long id) {

    }

    @Override
    public Optional<ReservationEntity> findById(final long id) {
        ReservationEntity reservationEntity = jdbcTemplate.queryForObject(
                "SELECT id, name, date, time FROM reservation",
                (resultSet, rowNum) -> {
                    ReservationEntity r = new ReservationEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );
                    return r;
                }
        );
        return Optional.ofNullable(reservationEntity);
    }
}
