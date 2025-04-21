package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation(name, date, time) VALUES(?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }
        , keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public boolean delete(Long id) {
        int deletedRow = jdbcTemplate.update(
                "DELETE FROM reservation WHERE id = ?",
                id
        );
        return deletedRow == 1;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                (rs, rowNum) -> {
                    return new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            LocalDate.parse(rs.getString("date")),
                            LocalTime.parse(rs.getString("time"))
                    );
                }
        );
    }
}
