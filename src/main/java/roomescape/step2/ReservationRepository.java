package roomescape.step2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT * FROM reservation";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return Reservation.toEntity(
                    rs.getLong("id"),
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date")),
                    LocalTime.parse(rs.getString("time"))
            );
        });
    }

    public Reservation saveReservation(ReservationRequest request) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, request.name());
                    ps.setString(2, request.date().toString());
                    ps.setString(3, request.time().toString());
                    return ps;
                }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return Reservation.toEntity(
                id,
                request.name(),
                request.date(),
                request.time());
    }

    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
