package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertReservation = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = "select id, name, date, time from reservation";
        List<Reservation> foundReservations = jdbcTemplate.query(
                sql, (rs, rowNum) -> {
                    long id = rs.getLong("id");
                    String name = rs.getString("name");
                    String date = rs.getString("date");
                    String time = rs.getString("time");
                    return new Reservation(id, toPerson(name), toReservationTime(date, time));
                }
        );
        return foundReservations;
    }

    private ReservationTime toReservationTime(String date, String time) {
        return new ReservationTime(LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time)));
    }

    private Person toPerson(String name) {
        return new Person(name);
    }

    public Reservation insert(Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getPersonName());
        parameters.put("date", reservation.getDate());
        parameters.put("time", reservation.getTime());
        long newId = insertReservation.executeAndReturnKey(parameters).longValue();

        return new Reservation(newId, reservation);
    }

    public int deleteById(long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
