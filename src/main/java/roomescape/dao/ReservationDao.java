package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDao {

    private static final String DATABASE = "reservation";

    private JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        List<Reservation> reservationResponses = new ArrayList<>();
        String sql = "SELECT id, name, date, time FROM " + DATABASE;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation
                            = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            LocalTime.parse(resultSet.getString("time"))
                    );
                    return reservation;
                }
        );
    }
}
