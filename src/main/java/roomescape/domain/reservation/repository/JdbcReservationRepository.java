package roomescape.domain.reservation.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.domain.Reservation;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAllReservations() {

        return jdbcTemplate.query(
            """
                SELECT r.id, r.name, r.date, COALESCE(rt.start_at, r.time) AS time
                FROM reservation r
                LEFT JOIN reservation_time rt ON r.time_id = rt.id
                """,
            (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                parseTime(rs.getString("time"))
            )
        );
    }

    @Override
    public Reservation save(Reservation reservation) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = createSavePreparedStatement(connection, reservation);

            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());

            if (reservation.getTimeId() != null) {
                ps.setLong(3, reservation.getTimeId());
            } else {
                ps.setString(3, reservation.getTime().toString());
            }

            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();

        return jdbcTemplate.queryForObject(
            """
                SELECT r.id, r.name, r.date, COALESCE(rt.start_at, r.time) AS time
                FROM reservation r
                LEFT JOIN reservation_time rt ON r.time_id = rt.id
                WHERE r.id = ?
                """,
            (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                parseTime(rs.getString("time"))
            ),
            key
        );
    }

    private LocalTime parseTime(String time) {
        if (time == null) {
            return null;
        }
        return LocalTime.parse(time);
    }

    private PreparedStatement createSavePreparedStatement(
        java.sql.Connection connection,
        Reservation reservation
    ) throws java.sql.SQLException {
        if (reservation.getTimeId() != null) {
            return connection.prepareStatement(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
        }

        return connection.prepareStatement(
            "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS
        );
    }

    @Override
    public void deleteReservationById(Long id) {
        jdbcTemplate.update(
            "DELETE FROM reservation WHERE id = ?",
            id
        );
    }
}
