package roomescape.model;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.dto.time.TimeResponse;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ReservationResponse> reservationMapper =
            (rs, rowNum) -> {
                Long reservationId = rs.getLong("reservation_id");
                String name = rs.getString("name");
                LocalDate date = LocalDate.parse(rs.getString("date"));
                Long timeId = rs.getLong("time_id");
                LocalTime startAt = LocalTime.parse(rs.getString("start_at"));
                Time time = new Time(timeId, startAt);
                return new ReservationResponse(reservationId, name, date, TimeResponse.from(time));
            };

    @Autowired
    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationResponse createReservation(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, reservation.getName());
                    preparedStatement.setString(2, reservation.getDate().toString());
                    preparedStatement.setLong(3, reservation.getTime().getId());
                    return preparedStatement;
                },
                keyHolder
        );

        Long id = keyHolder.getKey().longValue();

        return ReservationResponse.toDto(id, reservation);
    }

    public void deleteReservation(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<ReservationResponse> getReservations() {
        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, reservationMapper);
    }
}
