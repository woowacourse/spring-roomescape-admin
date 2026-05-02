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
import roomescape.reservationtime.domain.ReservationTime;

@Repository
@AllArgsConstructor
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Reservation> findAllReservations() {
        String sql = "SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at "
                + "FROM reservation r "
                + "INNER JOIN reservation_time t ON r.time_id = t.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ReservationTime reservationTime = ReservationTime.builder()
                    .id(rs.getLong("time_id"))
                    .startAt(LocalTime.parse(rs.getString("start_at")))
                    .build();

            return Reservation.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .date(LocalDate.parse(rs.getString("date")))
                    .time(reservationTime)
                    .build();
        });
    }

    public Reservation saveReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
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
