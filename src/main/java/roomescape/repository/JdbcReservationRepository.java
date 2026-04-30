package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class JdbcReservationRepository implements ReservationRepository {
    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) ->
            new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("time")
            );

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM reservation", RESERVATION_ROW_MAPPER);
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, reservation.name());
            ps.setString(2, reservation.date());
            ps.setString(3, reservation.time());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return new Reservation(
                generatedId,
                reservation.name(),
                reservation.date(),
                reservation.time()
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
