package roomescape.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class H2ReservationRepository implements ReservationRepository {
    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            );
    private final JdbcTemplate jdbcTemplate;

    public H2ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation", reservationRowMapper);
    }

    @Override
    public Optional<Reservation> findById(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM reservation WHERE id=?", reservationRowMapper, id));
    }

    @Override
    public Reservation save(Reservation reservation) {
        long update = jdbcTemplate.update("INSERT INTO Reservation (name, date, time) VALUES (?, ?, ?)",
                reservation.getName(), reservation.getDate(), reservation.getTime());

        return new Reservation(update, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id=?", id);
    }
}
