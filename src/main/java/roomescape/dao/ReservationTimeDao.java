package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.domain.ReservationTime;

import java.util.List;

@Component
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(ReservationTime reservationTime) {
        return jdbcTemplate.update("insert into reservation_time (start_at) values (?)",
                reservationTime.getStartAt());
    }

    public List<ReservationTime> readAll() {
        return jdbcTemplate.query("select * from reservation_time",
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getInt("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ));
    }

    public int delete(int id) {
        return jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}
