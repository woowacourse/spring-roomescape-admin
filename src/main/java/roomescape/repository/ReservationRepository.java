package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            LocalDateTime.of(LocalDate.parse(rs.getString("date")), LocalTime.parse(rs.getString("time")))
    );

    public Reservation findById(long id) {
        return jdbcTemplate.query("""
                        SELECT id, name, date, time 
                        FROM reservation 
                        WHERE id = ?
                   """,
                reservationRowMapper,
                id).stream()
                .findFirst()
                .orElse(null);
    }

    public long save(Reservation reservation) {
        final LocalDateTime reservationDateTime = reservation.getReservationDateTime();
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
                    final PreparedStatement ps = con.prepareStatement("""
                           MERGE INTO reservation r
                           USING (VALUES (?, ?, ?, ?)) t(id, name, date, time) ON r.id = t.id
                           WHEN MATCHED THEN
                               UPDATE SET
                                   name = t.name,
                                   date = t.date,
                                   time = t.time
                           WHEN NOT MATCHED THEN
                               INSERT (name, date, time)
                               VALUES (t.name, t.date, t.time)""", new String[]{"id"});
                    ps.setObject(1, reservation.getId());
                    ps.setString(2, reservation.getReservationName());
                    ps.setString(3, reservationDateTime.toLocalDate().format(dateFormatter));
                    ps.setString(4, reservationDateTime.toLocalTime().format(timeFormatter));
                    return ps;
                },
                keyHolder);
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        }

        return reservation.getId();
    }

    public void deleteById(long id) {
        jdbcTemplate.update("""
                DELETE FROM reservation
                WHERE id = ? """,
                id);
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
                        SELECT id, name, date, time 
                        FROM reservation
                   """,
                reservationRowMapper);
    }
}
