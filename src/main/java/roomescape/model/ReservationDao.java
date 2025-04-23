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

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ReservationResponse> reservationMapper =
            (rs, rowNum) -> {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                LocalDate date = LocalDate.parse(rs.getString("date"));
                LocalTime time = LocalTime.parse(rs.getString("time"));
                return new ReservationResponse(id, name, date, time);
            };

    @Autowired
    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationResponse createReservation(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, reservation.getName());
                    preparedStatement.setString(2, reservation.getDate().toString());
                    preparedStatement.setString(3, reservation.getTime().toString());
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
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(sql, reservationMapper);
    }
}
