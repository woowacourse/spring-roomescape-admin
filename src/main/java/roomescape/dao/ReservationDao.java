package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Name;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;
import roomescape.domain.reservation.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ReservationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationDao() {
    }

    private final RowMapper<Reservation> reservationRowMapper = ((rs, rowNum) -> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        LocalDate date = LocalDate.parse(rs.getString("date"), formatter);
        LocalTime time = LocalTime.parse(rs.getString("time"));
        return new Reservation(id, new Name(name), new ReservationDate(date), new ReservationTime(time));
    });

    public List<Reservation> findAllReservation() {
        String sql = "SELECT id, name, date, time FROM reservation";
        try {
            return jdbcTemplate.query(sql, reservationRowMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
