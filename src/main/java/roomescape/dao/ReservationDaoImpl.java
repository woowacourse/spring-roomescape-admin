package roomescape.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDaoImpl implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation", (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date"),
                rs.getTime("time")
        ));
    }

    @Override
    public void save(Reservation reservation) {
        if (existsById(reservation.getId())) {
            throw new IllegalArgumentException("duplicated id exists.");
        }
        jdbcTemplate.update("INSERT INTO reservation(id, name, date, time) VALUES(?,?,?,?)",
                reservation.getId(),
                reservation.getName(),
                Date.valueOf(reservation.getDate()),
                Time.valueOf(reservation.getTime()));
    }

    @Override
    public boolean deleteById(long id) {
        boolean exists = existsById(id);
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        return exists;
    }

    private boolean existsById(long id) {
        long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM reservation WHERE id = ?", Long.class, id);
        return count > 0;
    }
}
