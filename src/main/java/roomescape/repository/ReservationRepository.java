package roomescape.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

@Repository
public class ReservationRepository {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String findQuery = """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
            FROM reservation as r
            inner join reservation_time as t
            on r.time_id = t.id""";

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) ->
            new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    CustomDateTimeFormatter.getLocalDate(rs.getString("date")),
                    new ReservationTime(
                            rs.getLong("time_id"),
                            CustomDateTimeFormatter.getLocalTime(rs.getString("time_value"))
                    )
            );

    public List<Reservation> findReservations() {
        return jdbcTemplate.query(findQuery, reservationRowMapper);
    }
}
