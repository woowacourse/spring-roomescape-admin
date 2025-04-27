package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Person;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertReservation;
    private ReservationTimeDao reservationTimeDao;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertReservation = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
    }

    public List<Reservation> findAll() {
        String sql = "SELECT \n"
                + "    r.id as reservation_id, \n"
                + "    r.name, \n"
                + "    r.date, \n"
                + "    t.id as time_id, \n"
                + "    t.start_at as time_value \n"
                + "FROM reservation as r \n"
                + "inner join reservation_time as t \n"
                + "on r.time_id = t.id\n";
        List<Reservation> foundReservations = jdbcTemplate.query(
                sql, (rs, rowNum) -> {
                    long id = rs.getLong("id");
                    String name = rs.getString("name");
                    String date = rs.getString("date");
                    String startAt = rs.getString("start_at");
                    return new Reservation(id, new Person(name), LocalDate.parse(date), toReservationTime(startAt));
                }
        );
        return foundReservations;
    }

    private ReservationTime toReservationTime(String time) {
        return new ReservationTime(LocalTime.parse(time));
    }

    public Reservation insert(Reservation reservation, long timeId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getPersonName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", timeId);
        long newId = insertReservation.executeAndReturnKey(parameters).longValue();
        ReservationTime reservationTime = reservationTimeDao.findById(timeId);
        return new Reservation(newId, reservation, reservationTime);
    }

    public int deleteById(long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
