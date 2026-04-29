package roomescape.domain.reservation.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
            "SELECT id, name, date, time FROM reservation",
            (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime()
            )
        );
    }

    @Override
    public Reservation save(Reservation reservation) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());

            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();

        return jdbcTemplate.queryForObject(
            "SELECT id, name, date, time FROM reservation WHERE id = ?",
            (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime()
            ),
            key
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
