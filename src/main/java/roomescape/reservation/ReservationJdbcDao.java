package roomescape.reservation;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.time.Time;

@Repository
public class ReservationJdbcDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationJdbcDao(
            @Autowired JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long saveReservation(final Reservation reservation, final Long timeId) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.name());
        parameters.put("date", reservation.date());
        parameters.put("time_id", timeId);

        final Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        return id.longValue();
    }

    @Override
    public List<Reservation> findAllReservation() {
        final String query =
                "SELECT R.id, R.name, R.date, T.id AS time_id, T.start_at AS start_at "
                        + "FROM RESERVATION AS R INNER JOIN RESERVATION_TIME AS T "
                        + "ON R.time_id=T.id";

        final List<Reservation> reservations = jdbcTemplate.query(query, (rs, rowNum) -> {
            return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    new Time(rs.getLong("time_id"), rs.getTime("start_at").toLocalTime())
            );
        });
        return reservations;
    }

    @Override
    public Reservation findReservationById(final Long id) {
        final String query =
                "SELECT R.id, R.name, R.date, T.id AS time_id, T.start_at AS start_at "
                        + "FROM RESERVATION AS R INNER JOIN RESERVATION_TIME AS T "
                        + "ON R.time_id=T.id "
                        + "WHERE R.id=?";

        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    new Time(rs.getLong("time_id"), rs.getTime("start_at").toLocalTime())
            );
        }, id);
    }

    @Override
    public void deleteReservationById(final long id) {
        final String query = "DELETE FROM RESERVATION WHERE ID=?";
        final int updatedCount = jdbcTemplate.update(query, id);
        validateUpdateSuccess(updatedCount);
    }

    private static void validateUpdateSuccess(final int updatedCount) {
        if (updatedCount == 0) {
            throw new IllegalArgumentException("[ERROR]");
        }
    }
}
