package roomescape.reservation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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
    public Reservation saveReservation(final Reservation reservation) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(reservation);
        final Number id = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        return new Reservation(id.longValue(), reservation.name(), reservation.date(), reservation.time());
    }

    @Override
    public List<Reservation> findAllReservation() {
        final String query = "SELECT * FROM RESERVATION";
        final List<Reservation> reservations = jdbcTemplate.query(query, (rs, rowNum) -> {
            return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime()
            );
        });
        return reservations;
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
