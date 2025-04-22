package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Person;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {
    private JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "select id, name, date, time from reservation";
        List<Reservation> foundReservations = jdbcTemplate.query(
                sql, (rs, rowNum) -> {
                    long id = rs.getLong("id");
                    String name = rs.getString("name");
                    String date = rs.getString("date");
                    String time = rs.getString("time");
                    Reservation reservation = new Reservation(id, toPerson(name), toReservationTime(date, time));
                    return reservation;
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
        String sql = "insert into reservation (name, date, time) values(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getPersonName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Reservation(id, reservation);
    }

    public long deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
