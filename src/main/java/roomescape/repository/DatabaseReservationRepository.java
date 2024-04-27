package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.controller.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class DatabaseReservationRepository implements ReservationRepository {

    private final String findAllReservationQuery = """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
            FROM reservation as r
            inner join reservation_time as t
            on r.time_id = t.id""";

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) ->
            new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    new ReservationTime(
                            rs.getLong("time_id"),
                            rs.getTime("time_value").toLocalTime()
                    )
            );

    private final JdbcTemplate jdbcTemplate;

    public DatabaseReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findReservations() {
        return jdbcTemplate.query(findAllReservationQuery, reservationRowMapper);
    }

    @Override
    public Optional<Reservation> findReservationById(Long createdReservationId) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(findAllReservationQuery + " WHERE r.id = ?",
                        reservationRowMapper,
                        createdReservationId)
        );
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        long createdReservationId = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", reservation.getReservationTime().getId()
        )).longValue();

        return findReservationById(createdReservationId).get();
    }

    @Override
    public void deleteReservationById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
