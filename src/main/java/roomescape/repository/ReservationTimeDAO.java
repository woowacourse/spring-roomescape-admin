package roomescape.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAllReservations() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) ->
                new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ));
    }
}
