package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationResDto;
import roomescape.dto.ReservationTimeResDto;

@Repository
public class QueryingDAO {

    private final JdbcTemplate jdbcTemplate;

    public QueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationResDto> findAllReservations() {
        return jdbcTemplate.query("SELECT * FROM reservation", (resultSet, rowNum) -> new ReservationResDto(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        ));
    }

    public List<ReservationTimeResDto> findAllReservationTimes() {
        return jdbcTemplate.query("SELECT * FROM reservation_time", (resultSet, rowNum) -> new ReservationTimeResDto(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        ));
    }
}
