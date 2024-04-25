package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ReservationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long save(Reservation reservation) {
        String query = "INSERT into reservation(name, date, time_id) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    query,
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Reservation> readAll() {
        String query = "SELECT " +
                "    r.id as reservation_id, " +
                "    r.name as reservation_name, " +
                "    r.date as reservation_date, " +
                "    t.id as time_id, " +
                "    t.start_at as time_value " +
                "FROM reservation as r " +
                "inner join reservation_time as t " +
                "on r.time_id = t.id";

        List<Reservation> reservations = jdbcTemplate.query(query,
                (rs, rowNum) -> {
                    Long timeId = rs.getLong("time_id");
                    String startTime = rs.getString("time_value");
                    ReservationTime reservationTime = new ReservationTime(timeId, startTime);

                    return new Reservation(
                            rs.getLong("reservation_id"),
                            rs.getString("reservation_name"),
                            rs.getObject("reservation_date", LocalDate.class),
                            reservationTime
                    );
                }
        );
        return reservations;
    }

    public void delete(Long id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    public String findStartTimeByTimeId(Long timeId) {
        String query = "SELECT start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(query, String.class, timeId);
    }
}
