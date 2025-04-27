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

@Repository
public class ReservationDAOImpl implements ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDAOImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservation() {
        final String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            final Long id = resultSet.getLong("id");
            final String name = resultSet.getString("name");
            final LocalDate date = LocalDate.parse(resultSet.getString("date"));
            final LocalTime time = LocalTime.parse(resultSet.getString("time"));
            return new Reservation(id, name, date, time);
        });
    }

    public Long insertReservation(final Reservation reservation) {
        final String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate()
                    .toString());
            ps.setString(3, reservation.getTime()
                    .toString());
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
