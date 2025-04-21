package roomescape.reservation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        jdbcTemplate.update(query, reservation.name(), reservation.date(), reservation.time());
        return reservation;
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
}
