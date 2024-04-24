package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.controller.dto.CreateReservationTimeRequest;
import roomescape.domain.ReservationTime;

import java.util.List;

@Component
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(CreateReservationTimeRequest request) {
        return jdbcTemplate.update("insert into reservation_time (start_at) values (?)",
                request.startAt());
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select * from reservation_time",
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getInt("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ));
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}
