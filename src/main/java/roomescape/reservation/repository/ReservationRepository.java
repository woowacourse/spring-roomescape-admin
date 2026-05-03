package roomescape.reservation.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@Repository
@AllArgsConstructor
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Reservation> findAllReservations() {
        String sql = "SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at "
                + "FROM reservation r "
                + "INNER JOIN reservation_time t ON r.time_id = t.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ReservationTime reservationTime = ReservationTimeRepository.ROW_MAPPER.mapRow(rs, rowNum);

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

        Number generatedId = Optional.ofNullable(keyHolder.getKey())
                .orElseThrow(() -> new RuntimeException("[ERROR] DB 아이디 생성에 실패했습니다."));

        return Reservation.builder()
                .id(generatedId.longValue())
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
