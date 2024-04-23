package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class ReservationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Reservation save(Reservation reservation) {
        String query = "INSERT into reservation(name, date, time) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    query,
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return Reservation.toEntity(id, reservation);
    }

    public List<Reservation> readAll() {
        String query = "SELECT * FROM reservation";

        List<Reservation> reservations = jdbcTemplate.query(query,
                (rs, rowNum) -> {
                    Reservation reservation = new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getObject("date", LocalDate.class),
                            rs.getObject("time", LocalTime.class)
                    );
                    return reservation;
                }
        );
        return reservations;
    }

    public void delete(Long id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
