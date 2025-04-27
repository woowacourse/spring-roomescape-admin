package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
public class ReservationDAOImpl implements ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDAOImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservation() {
        final String sql = """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
            FROM 
                reservation as r
                inner join reservation_time as t
                on r.time_id = t.id""";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            final Long id = resultSet.getLong("id");
            final String name = resultSet.getString("name");
            final LocalDate date = LocalDate.parse(resultSet.getString("date"));
            final Long timeId = resultSet.getLong("time_id");
            final LocalTime timeValue = LocalTime.parse(resultSet.getString("time_value"));
            return new Reservation(id, name, date, new Time(timeId, timeValue));
        });
    }

    public Long insertReservation(final Reservation reservation) {
        final String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate()
                    .toString());
            ps.setLong(3, reservation.getTime()
                    .getId());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            return -1L;
        }
        return keyHolder.getKey()
                .longValue();
    }

    public int deleteReservationById(final Long id) {
        final String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
