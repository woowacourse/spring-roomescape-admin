package roomescape.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateResponse;
import roomescape.dto.TimeCreateResponse;

public class QueryingDAO {
    private JdbcTemplate jdbcTemplate;

    public QueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime findTimeById(Long reservationId) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getString("start_at")
                ),
                reservationId
        );
    }

    public List<ReservationCreateResponse> findAllReservations() {
        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                INNER JOIN reservation_time as t
                  ON r.time_id = t.id
                """;
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    TimeCreateResponse timeCreateResponse = mapToTimeCreateResponse(resultSet);
                    return new ReservationCreateResponse(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            timeCreateResponse
                    );
                });
    }

    public List<TimeCreateResponse> findAllTimes() {
        String sql = "select id as time_id, start_at as time_value from reservation_time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> mapToTimeCreateResponse(resultSet));
    }

    private TimeCreateResponse mapToTimeCreateResponse(ResultSet rs) throws SQLException {
        return new TimeCreateResponse(
                rs.getLong("time_id"),
                rs.getString("time_value")
        );
    }
}
