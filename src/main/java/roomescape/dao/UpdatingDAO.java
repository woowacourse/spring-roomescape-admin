package roomescape.dao;

import java.sql.PreparedStatement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class UpdatingDAO {
    private JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };

    public void insert(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void insert(ReservationTime reservationTime) {
        String sql = "insert into reservation_time (start_at) values (?)";
        jdbcTemplate.update(sql, reservationTime.getStartAt());
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }

    public int deleteTime(Long id) {
        String sql = "delete from reservation_time where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }

    public Long insertWithKeyHolder(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Long insertWithKeyHolder(ReservationTime reservationTime) {
        String sql = "insert into reservation_time (start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation_time (start_at) values (?)",
                    new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
