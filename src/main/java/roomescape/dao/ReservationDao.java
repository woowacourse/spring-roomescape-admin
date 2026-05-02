package roomescape.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at " +
                        "FROM reservation r " +
                        "INNER JOIN reservation_time t ON r.time_id = t.id",
                (rs, rowNum) -> {
                    ReservationTime time = new ReservationTime(
                            rs.getLong("time_id"),
                            rs.getString("start_at")
                    );
                    return new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("date"),
                            time
                    );
                }
        );
    }

    public ReservationTime findTimeById(Long timeId) {
        return jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getString("start_at")
                ),
                timeId
        );
    }

    public Reservation save(Reservation reservation, ReservationTime reservationTime) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate());
        params.put("time_id", reservation.getTime().getId());

        Long id = insert.executeAndReturnKey(params).longValue();
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservationTime);

    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

}
