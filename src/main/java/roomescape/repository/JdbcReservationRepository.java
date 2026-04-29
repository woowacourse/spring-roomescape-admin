package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final JdbcTemplate template;

    public JdbcReservationRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Long createReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName().value());
            ps.setString(2, dateFormatter.format(reservation.getDate()));
            ps.setString(3, timeFormatter.format(reservation.getTime()));
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        return key;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?;";

        template.update(sql, id);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation;";

        List<Reservation> reservations = template.query(sql, reservationRowMapper());

        return reservations;
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return ((rs, rowNum) -> {
            Reservation reservation = new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("time"));
            return reservation;
        });
    }
}
