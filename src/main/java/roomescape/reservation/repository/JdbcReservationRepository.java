package roomescape.reservation.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.entity.Reservation;
import roomescape.time.entity.ReservationTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private static final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = ReservationTime.of(
                resultSet.getLong("time_id"),
                resultSet.getTime("start_at").toLocalTime()
        );

        return Reservation.of(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                reservationTime
        );
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        final String sql =
                "SELECT " +
                        "r.id, " +
                        "r.name, " +
                        "r.date, " +
                        "t.id as time_id, " +
                        "t.start_at " +
                        "FROM reservation r " +
                        "INNER JOIN reservation_time t " +
                        "ON r.time_id = t.id";

        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @Override
    public Reservation save(Reservation reservation) {
        final String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return reservation.withId(id);
    }

    @Override
    public void deleteById(Long id) {
        final String sql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

}
