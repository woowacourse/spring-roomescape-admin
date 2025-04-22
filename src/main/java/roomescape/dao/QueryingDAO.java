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
        String sql = """
                SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationResDto(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                new ReservationTimeResDto(
                        resultSet.getLong("time_id"),
                        resultSet.getTime("time_value").toLocalTime()
                )
        ));
    }

    public List<ReservationTimeResDto> findAllReservationTimes() {
        return jdbcTemplate.query("SELECT * FROM reservation_time", (resultSet, rowNum) -> new ReservationTimeResDto(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        ));
    }
}
