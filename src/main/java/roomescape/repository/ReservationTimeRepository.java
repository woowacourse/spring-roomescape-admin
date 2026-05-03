package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
@RequiredArgsConstructor
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final RowMapper<ReservationTime> reservationTimeRowMapper = (rs, rowNum) -> new ReservationTime(
            rs.getLong("id"),
            LocalTime.parse(rs.getString("start_at"))
    );

    public long save(ReservationTime reservationTime) {
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement ps = con.prepareStatement("""
                           MERGE INTO reservation_time r
                           USING (VALUES (?, ?)) t(id, start_at) ON r.id = t.id
                            WHEN MATCHED THEN
                                UPDATE SET
                                    start_at = t.start_at
                            WHEN NOT MATCHED THEN
                                INSERT (start_at)
                                VALUES (t.start_at)""", new String[]{"id"});
            ps.setObject(1, reservationTime.getId());
            ps.setString(2, reservationTime.getReservationTime().format(timeFormatter));
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        }

        return reservationTime.getId();
    }

    public ReservationTime findById(long id) {
        return jdbcTemplate.query("""
                        SELECT id, start_at
                        FROM reservation_time
                        WHERE id = ?
                    """,
                reservationTimeRowMapper,
                id).stream()
                .findFirst()
                .orElse(null);
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("""
                SELECT id, start_at
                FROM reservation_time
                """, reservationTimeRowMapper);
    }

    public void deleteById(long id) {
        jdbcTemplate.update("""
                DELETE FROM reservation_time
                WHERE id = ?
                """, id);
    }
}
