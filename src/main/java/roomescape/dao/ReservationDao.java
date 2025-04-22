package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.model.Reservation;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> selectAllReservation() {
        String sql = "SELECT * from reservation";

        List<Reservation> reservationList = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(resultSet.getLong("id"), resultSet.getString("name"),
                    LocalDate.parse(resultSet.getString("date")), LocalTime.parse(resultSet.getString("time")));
            return reservation;
        });

        return reservationList;
    }

    public Reservation addReservation(Reservation reservation) {
        final Map<String, String> parameters = Map.of("name", reservation.getName(), "date",
                reservation.getDate().toString(), "time", reservation.getTime().toString());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        Long id = (Long) simpleJdbcInsert.executeAndReturnKey(parameters);

        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public int deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id=?";

        int effectedRow = jdbcTemplate.update(sql, id);

        return effectedRow;
    }
}
