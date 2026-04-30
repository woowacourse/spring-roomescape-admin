package roomescape.reservation.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;

@RequiredArgsConstructor
@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                """
                        SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at as time_value
                        FROM reservation r
                        JOIN reservation_time t ON r.time_id = t.id
                        """,
                (rs, rowNum) -> (
                        Reservation.builder()
                                .id(rs.getLong("id"))
                                .name(rs.getString("name"))
                                .date(LocalDate.parse(rs.getString("date")))
                                .time(ReservationTime.builder()
                                        .id(rs.getLong("time_id"))
                                        .startAt(LocalTime.parse(rs.getString("time_value")))
                                        .build())
                                .build()
                )
        );
    }

    @Override
    public Long save(Reservation reservation) {
        String formattedDate = reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, formattedDate);
            ps.setLong(3, reservation.getTimeId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, Long timeId) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT 1 FROM reservation WHERE date = ? AND time_id = ?)",
                Boolean.class,
                formattedDate,
                timeId);
    }
}
