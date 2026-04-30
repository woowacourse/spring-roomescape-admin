package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id , t.start_at FROM reservation r INNER JOIN reservation_time t ON r.time_id = t.id";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    return new Reservation(
                            rs.getLong("reservation_id"),
                            rs.getString("name"),
                            rs.getString("date"),
                            new ReservationTime(rs.getLong("time_id"), rs.getString("start_at"))
                    );
                });
    }

    public Reservation findReservationById(Long id) {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at FROM reservation r INNER JOIN reservation_time t ON r.time_id = t.id WHERE r.id = ?";

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> {

                    return new Reservation(
                            rs.getLong("reservation_id"),
                            rs.getString("name"),
                            rs.getString("date"),
                            new ReservationTime(rs.getLong("time_id"), rs.getString("start_at"))
                    );
                }, id);
    }

    public Long insertWithKeyHolder(ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation (name,date,time_id) VALUES(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationRequest.name());
            ps.setString(2, reservationRequest.date());
            ps.setLong(3, reservationRequest.timeId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
