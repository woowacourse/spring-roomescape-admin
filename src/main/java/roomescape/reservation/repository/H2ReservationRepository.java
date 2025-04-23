package roomescape.reservation.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    @Override
    public Reservation put(final Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setTime(3, Time.valueOf(reservation.getTime()));
            return ps;
        }, keyHolder);

        long generatedId = keyHolder.getKey().longValue();
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
