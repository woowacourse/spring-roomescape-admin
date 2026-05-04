package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationQueryingRepository {
    private JdbcTemplate jdbcTemplate;

    public ReservationQueryingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        ReservationTime time = new ReservationTime(
                resultSet.getLong("time_id"),
                LocalTime.parse(resultSet.getString("start_at"))
        );

        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                time
        );
    };

    public List<Reservation> findAll() {
        String sql = """
        SELECT 
            r.id as id, 
            r.name as name, 
            r.date as date, 
            t.id as time_id, 
            t.start_at as start_at 
        FROM reservation r 
        INNER JOIN reservation_time t ON r.time_id = t.id
        """;
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
