package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        String sql = "select * from reservation";
        return jdbcTemplate.query(sql,
            (resultSet, RowNum) -> {
                Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    new Person(resultSet.getString("name")),
                    new ReservationTime(
                        LocalDateTime.of(
                            LocalDate.parse(resultSet.getString("date")),
                            LocalTime.parse(resultSet.getString("time"))
                        ))
                );
                return reservation;
            });
    }
}
