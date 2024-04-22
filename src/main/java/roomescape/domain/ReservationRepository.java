package roomescape.domain;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations;
    private final AtomicLong index;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationRepository() {
        reservations = new ArrayList<>();
        index = new AtomicLong(1L);
    }


    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM reservation", reservationRowMapper());
    }

    public Reservation findById(long id) {
        return jdbcTemplate.queryForObject("SELECT id, name, date, time FROM reservation WHERE id = ?",
                reservationRowMapper(), id);
    }

    public Reservation create(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return findById(id);
    }

    public void remove(Reservation reservation) {
        reservations.remove(reservation);
    }

    public void removeAll() {
        reservations.clear();
        index.set(1L);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            );
            return reservation;
        };
    }
}
