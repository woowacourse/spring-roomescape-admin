package roomescape.reservation.dao;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

@Repository
public class ReservationJdbcDao implements ReservationDao{
    private static final String SELECT_QUERY =
    """
    SELECT
        r.id as reservation_id,
        r.name,
        r.date,
        t.id as time_id,
        t.start_at as time_value
    FROM reservation as r
    INNER JOIN reservation_time as t
        ON r.time_id = t.id
    """;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationJdbcDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAllReservations() {
        return jdbcTemplate.query(SELECT_QUERY, (rs, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(
                    rs.getLong("time_id"),
                    rs.getTime("time_value").toLocalTime()
            );

            return new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    reservationTime
            );
        });
    }

    @Override
    public Reservation insertReservation(final Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", Date.valueOf(reservation.getDate()));
        parameters.put("time_id", reservation.getTime().getId());
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Reservation(newId.longValue(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public void removeReservation(final long id) {
        String sql = "DELETE from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
