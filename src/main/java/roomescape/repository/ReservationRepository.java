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
import roomescape.domain.ReservationTime;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        final ReservationTime reservationTime = new ReservationTime(rs.getLong("time_id"), LocalTime.parse(rs.getString("start_at")));
        return new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                reservationTime);
    };

    public Reservation findById(long id) {
        return jdbcTemplate.query("""
                        SELECT r.id, r.name, r.date, rt.id as time_id, rt.start_at
                        FROM reservation r
                        inner join reservation_time rt on r.time_id = rt.id
                        WHERE r.id = ?
                   """,
                reservationRowMapper,
                id).stream()
                .findFirst()
                .orElse(null);
    }

    public long save(Reservation reservation) {
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
                    final PreparedStatement ps = con.prepareStatement("""
                           MERGE INTO reservation r
                           USING (VALUES (?, ?, ?, ?)) t(id, name, date, time_id) ON r.id = t.id
                           WHEN MATCHED THEN
                               UPDATE SET
                                   name = t.name,
                                   date = t.date,
                                   time_id = t.time_id
                           WHEN NOT MATCHED THEN
                               INSERT (name, date, time_id)
                               VALUES (t.name, t.date, t.time_id)""", new String[]{"id"});
                    ps.setObject(1, reservation.getId());
                    ps.setString(2, reservation.getReservationName());
                    ps.setString(3, reservation.getReservationDate().format(dateFormatter));
                    ps.setLong(4, reservation.getReservationTime().getId());
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
                        SELECT r.id, r.name, r.date, rt.id as time_id, rt.start_at
                        FROM reservation r
                        inner join reservation_time rt on r.time_id = rt.id
                   """,
                reservationRowMapper);
    }
}
