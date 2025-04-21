package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Person;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class QueryingDao {

    private final JdbcTemplate jdbcTemplate;

    public QueryingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservation() {
        String sql = "select r.id as reservation_id, r.name, r.date, t.id as time_id,  t.start_at as time_value from reservation as r inner join reservation_time as t on r.time_id = t. id";
        return jdbcTemplate.query(sql,
            (resultSet, RowNum) -> {
                Reservation reservation = new Reservation(
                    resultSet.getLong("reservation_id"),
                    new Person(resultSet.getString("name")),
                    LocalDate.parse(resultSet.getString("date")),
                    new ReservationTime(
                        resultSet.getLong("time_id"),
                        LocalTime.parse(resultSet.getString("time_value")))
                );
                return reservation;
            });
    }
}
