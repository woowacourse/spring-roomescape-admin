package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservationtime.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.start_at as time_value \n" +
                "FROM reservation as r \n" +
                "inner join reservation_time as t \n" +
                "on r.time_id = t.id";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) ->
                new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        new ReservationTime(resultSet.getLong("time_id"),
                                LocalTime.parse(resultSet.getString("time_value")))));
    }

    public Long save(String name, LocalDate date, Long timeId) {
        String sql = "INSERT INTO reservation(name,date,time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setString(2, String.valueOf(date));
            ps.setLong(3, timeId);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
