package roomescape;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void deleteReservationTime(Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public ReservationTime addReservationTime(ReservationTime reservationTime) {
        String sql = "insert into reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservationTime.getStartTime().toString());
            return ps;
        }, keyHolder);

        return findReservationTimeById(keyHolder.getKey().longValue());
    }

    public List<ReservationTime> findAllReservationTime() {
        String sql = "select id, start_at from reservation_time";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    return new ReservationTime(
                            resultSet.getLong("id"),
                            LocalTime.parse(resultSet.getString("start_at"))
                    );
                }
        );
    }

    public ReservationTime findReservationTimeById(Long id) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) ->
                        new ReservationTime(
                                resultSet.getLong("id"),
                                LocalTime.parse(resultSet.getString("start_at"))
                        ), id);
    }

    public List<Reservation> findAllReservation() {
        String sql = """
                    select r.id as reservation_id,   
                    r.name, r.date, 
                    t.id as reservation_time_id,
                    t.start_at as time_value
                    from reservation as r 
                    inner join reservation_time as t 
                    on r.time_id = t.id
                """;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime time = new ReservationTime(
                            resultSet.getLong("reservation_time_id"),
                            LocalTime.parse(resultSet.getString("time_value"))
                    );
                    return new Reservation(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            time
                    );
                }

        );
    }

    public Long add(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
