package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;

import java.util.List;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(Reservation reservation) {
        return jdbcTemplate.update("insert into reservation (name, date, time) values (?, ?, ?)",
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }

    public List<Reservation> readAll() {
        return jdbcTemplate.query("select * from reservation",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime()
                ));
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
