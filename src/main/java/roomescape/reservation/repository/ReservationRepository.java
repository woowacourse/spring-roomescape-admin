package roomescape.reservation.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

@Repository
@AllArgsConstructor
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Reservation> findAllReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                LocalTime.parse(rs.getString("time"))
        ));
    }

    public Reservation saveReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        },  keyHolder);

        Long generatedId = keyHolder.getKey().longValue();

        return Reservation.builder()
                .id(generatedId)
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build();
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
