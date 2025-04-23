package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.domain_entity.Id;
import roomescape.domain_entity.Reservation;

@Component
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        List<Reservation> reservations = jdbcTemplate.query(
                "select id, name, date, time from reservation",
                (rs, rowNum) ->
                        new Reservation(
                                new Id(rs.getLong("id")),
                                rs.getString("name"),
                                LocalDate.parse(rs.getString("date")),
                                LocalTime.parse(rs.getString("time"))
                        )
        );
        return reservations;
    }

}
