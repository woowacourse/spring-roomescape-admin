package roomescape.reservation;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation saveReservation(Reservation wantToSaveReservation) {
        String query = "INSERT INTO RESERVATION (name, date, time) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    query, new String[]{"id"});
            preparedStatement.setString(1, wantToSaveReservation.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(wantToSaveReservation.getDate()));
            preparedStatement.setTime(3, java.sql.Time.valueOf(wantToSaveReservation.getTime()));
            return preparedStatement;
        }, keyHolder);

        return Reservation.toEntity(wantToSaveReservation, keyHolder.getKey().longValue());
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
