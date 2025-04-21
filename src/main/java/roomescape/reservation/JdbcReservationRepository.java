package roomescape.reservation;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveReservation(Reservation wantToSaveReservation) {
        String query = "INSERT INTO RESERVATION (name, date, time) values (?,?,?)";
        jdbcTemplate.update(query, wantToSaveReservation.getName(), wantToSaveReservation.getDate(), wantToSaveReservation.getTime());
    }

    @Override
    public int deleteReservation(Long wantToDeleteId) {
        String query = "DELETE FROM RESERVATION WHERE id = ?";
        return jdbcTemplate.update(query, Long.valueOf(wantToDeleteId));
    }

    @Override
    public Reservation findReservationById(Long wandToFindId) {
        String query = "String select id, name, date, time FROM RESERVATION";
        return jdbcTemplate.queryForObject(
                query,
                (rs, rowNum) -> {
                    Reservation reservation = new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getDate("date").toLocalDate(),
                            rs.getTime("time").toLocalTime()
                    );
                    return reservation;
                }
                ,wandToFindId
        );
    }

    @Override
    public List<Reservation> findAllReservations() {
        String sql = "SELECT id, name, date, time FROM RESERVATION";

        return jdbcTemplate.query(
                sql,
                (result, rowNum) -> {
                    Reservation reservation = new Reservation(
                            result.getLong("id"),
                            result.getString("name"),
                            result.getDate("date").toLocalDate(),
                            result.getTime("time").toLocalTime()
                    );
                    return reservation;
                }
        );
    }

}
