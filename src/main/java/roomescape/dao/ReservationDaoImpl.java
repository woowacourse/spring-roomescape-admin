package roomescape.dao;

import java.sql.Date;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDaoImpl implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
                        SELECT r.id as reservation_id, r.name, r.date,
                            t.id as time_id, t.start_at as time_value
                        FROM reservation as r
                        inner join reservation_time as t
                        on r.time_id = t.id""",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("reservation_id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        new ReservationTime(rs.getLong("time_id"),
                                rs.getTime("time_value"))
                ));
    }

    @Override
    public void save(Reservation reservation) {
        if (existsById(reservation.getId())) {
            throw new IllegalArgumentException("duplicated id exists.");
        }
        jdbcTemplate.update("INSERT INTO reservation(id, name, date, time_id) VALUES(?,?,?,?)",
                reservation.getId(),
                reservation.getName(),
                Date.valueOf(reservation.getDate()),
                reservation.getTime().getId());
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
