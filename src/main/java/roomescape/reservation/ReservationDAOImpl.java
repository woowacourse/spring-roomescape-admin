package roomescape.reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDAOImpl implements ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDAOImpl(
            @Autowired JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation saveReservation(final Reservation reservation) {
        final String query = "INSERT INTO RESERVATION(name, date, time) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connect -> {
            final PreparedStatement preparedStatement = connect.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, reservation.name());
            preparedStatement.setDate(2, Date.valueOf(reservation.date()));
            preparedStatement.setTime(3, Time.valueOf(reservation.time()));
            return preparedStatement;
        }, keyHolder);

        final long key = keyHolder.getKey().longValue();
        return reservation.writeId(key);
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
